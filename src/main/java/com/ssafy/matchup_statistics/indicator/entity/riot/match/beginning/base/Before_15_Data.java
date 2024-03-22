package com.ssafy.matchup_statistics.indicator.entity.riot.match.beginning.base;


import com.ssafy.matchup_statistics.global.dto.response.MatchTimelineResponseDto;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.LaneInfo;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.LaneType;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.TeamPosition;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Data
@Slf4j
public class Before_15_Data {

    private CommonData commonData = new CommonData();
    private JgData jgData = new JgData();
    private BottomData bottomData = new BottomData();

    public Before_15_Data(LaneInfo laneInfo, MatchTimelineResponseDto matchTimelineResponseDto) {
        for (int minute = 0; minute <= 15; minute++) {
            MatchTimelineResponseDto.Frame frame = matchTimelineResponseDto.getInfo().getFrames().get(minute);
            frame.getEvents().forEach(event -> {

                // 탑, 미드
                if (laneInfo.getTeamPosition().equals(TeamPosition.TOP) || laneInfo.getTeamPosition().equals(TeamPosition.MIDDLE)) {
                    switch (event.type) {
                        case "TURRET_PLATE_DESTROYED": {
                            countTurretPlateDestroy(laneInfo, event);
                            break;
                        }
                        case "CHAMPION_KILL": {
                            countSoloKill(laneInfo, event);
                            break;
                        }
                    }
                }

                // 정글
                else if (laneInfo.getTeamPosition().equals(TeamPosition.JUNGLE)) {
                    if (event.type.equals("CHAMPION_KILL")) {
                        if (judgeIsOurKill(laneInfo.getMyTeamId(), event.killerId)) addMyKillCount(laneInfo, event);
                        else addOppositeKillCount(laneInfo, event);
                    }
                }

                // 원딜
                else if (laneInfo.getTeamPosition().equals(TeamPosition.BOTTOM)) {
                    switch (event.type) {
                        case "TURRET_PLATE_DESTROYED": {
                            countTurretPlateDestroy(laneInfo, event);
                            break;
                        }
                        case "CHAMPION_KILL": {
                            counterDuoKill(laneInfo, event);
                            break;
                        }
                    }
                }

                // 서폿
                else if (laneInfo.getTeamPosition().equals(TeamPosition.UTILITY)) {
                    switch (event.type) {
                        case "ITEM_DESTROYED": {
                            checkSupportItemFinishedTime(laneInfo, event);
                            break;
                        }
                        case "CHAMPION_KILL": {
                            counterDuoKill(laneInfo, event);
                            break;
                        }
                    }
                }

            });
        }
    }

    public void countTurretPlateDestroy(LaneInfo laneInfo,
                                        MatchTimelineResponseDto.Event event) {
        if (laneInfo.getTeamPosition().equals(TeamPosition.JUNGLE)) return;
        LaneType myLaneType = laneInfo.getTeamPosition().parseLaneType();
        if (event.laneType.equals(myLaneType.toString())) {
            if (event.teamId == laneInfo.getMyTeamId()) commonData.myTurretPlateDestroyCount++;
            else commonData.oppositeTurretPlateDestroyCount++;
        }
    }

    public void countSoloKill(LaneInfo laneInfo, MatchTimelineResponseDto.Event event) {

        // 관여한 사람이 없을때만 학인
        if (event.assistingParticipantIds == null)

            // 내가 상대방 죽였을때
            if (event.killerId == laneInfo.getMyLaneNumber() && event.victimId == laneInfo.getOppositeLaneNumber())
                commonData.mySoloKillCount++;

                // 상대가 나를 죽였을떄
            else if (event.killerId == laneInfo.getOppositeLaneNumber() && event.victimId == laneInfo.getMyLaneNumber())
                commonData.oppositeSoloKillCount++;
    }

    private void counterDuoKill(LaneInfo laneInfo, MatchTimelineResponseDto.Event event) {
        int[] ourBottomDuoNumbers = new int[]{laneInfo.getMyLaneNumber(), laneInfo.getMyBottomDuoNumber()};
        int[] oppositeDuoNumbers = new int[]{laneInfo.getOppositeLaneNumber(), laneInfo.getOppositeBottomDuoNumber()};

        // 관여한 사람이 봇듀오가 아닐때만 확인
        if (event.assistingParticipantIds != null) {
            for (int assist : event.assistingParticipantIds) {
                if (Arrays.stream(ourBottomDuoNumbers).noneMatch(our -> our == assist)
                        && Arrays.stream(oppositeDuoNumbers).noneMatch(opp -> opp == assist))
                    return;
            }
        }

        // 상대 봇듀오가 죽었을때 상대 봇듀오 킬
        if (Arrays.stream(oppositeDuoNumbers).anyMatch(v -> v == event.victimId)) {
            if (Arrays.stream(ourBottomDuoNumbers).anyMatch(k -> k == event.killerId)) {
                bottomData.myDuoKillCount++;
            }

            // 우리 봇듀오가 죽었을때 상대 봇듀오 킬
        } else if (Arrays.stream(ourBottomDuoNumbers).anyMatch(v -> v == event.victimId)) {
            if (Arrays.stream(oppositeDuoNumbers).anyMatch(k -> k == event.killerId)) {
                bottomData.oppositeDuoKillCount++;
            }
        }
    }

    private boolean judgeIsOurKill(int myTeamId, int killerId) {
        int[] team_100_participants = new int[]{1, 2, 3, 4, 5};

        // 우리팀이 100이고 1, 2, 3, 4, 5 중에 킬이 나올때
        if (myTeamId == 100 && Arrays.stream(team_100_participants).anyMatch(value -> value == killerId)) return true;

            //  우리팀이 200이고 1, 2, 3, 4, 5에서 킬이 나오지 않았을 때
        else if (myTeamId == 200 && Arrays.stream(team_100_participants).noneMatch(value -> value == killerId))
            return true;

            // 상대 킬
        else return false;
    }

    private void addMyKillCount(LaneInfo laneInfo, MatchTimelineResponseDto.Event event) {
        jgData.myTotalKillCount++;
        // 내 킬
        if (event.killerId == laneInfo.getMyLaneNumber()) {
            jgData.myKillCount++;

            // 상대 진영 킬
            int killedSpotTeam = event.position.x + event.position.y < 16000 ? 100 : 200;
            if (killedSpotTeam != laneInfo.getMyTeamId()) {
                jgData.myKillInvolvementInEnemyCamp++;
            }
        }
        // 내 어시
        else if (event.assistingParticipantIds != null &&
                event.assistingParticipantIds.stream().anyMatch(a ->
                        a.equals(laneInfo.getMyLaneNumber())))
            jgData.myAssistCount++;
    }

    private void addOppositeKillCount(LaneInfo laneInfo, MatchTimelineResponseDto.Event event) {
        // 상대 킬
        if (event.killerId == laneInfo.getOppositeLaneNumber()) {
            jgData.oppositeKillCount++;

            // 우리 진영 킬
            int killedSpotTeam = event.position.x + event.position.y < 16000 ? 100 : 200;
            if (killedSpotTeam == laneInfo.getMyTeamId()) {
                jgData.oppositeKillInvolvementInEnemyCamp++;
            }
        }
        // 상대 어시
        else if (event.assistingParticipantIds != null &&
                event.assistingParticipantIds.stream().anyMatch(a ->
                        a.equals(laneInfo.getOppositeLaneNumber()))) {
            jgData.oppositeAssistCount++;
        }
    }

    private void checkSupportItemFinishedTime(LaneInfo laneInfo, MatchTimelineResponseDto.Event event) {
        if (event.itemId != 3866) return;
        if (laneInfo.getMyLaneNumber() == event.getParticipantId())
            bottomData.mySupportItemFinishedTime = event.timestamp;
        else if (laneInfo.getOppositeLaneNumber() == event.getParticipantId())
            bottomData.oppositeSupportItemFinishedTime = event.timestamp;
    }

    @Data
    public static class CommonData {
        private int myTurretPlateDestroyCount;
        private int oppositeTurretPlateDestroyCount;
        private int mySoloKillCount;
        private int oppositeSoloKillCount;
    }

    @Data
    public static class JgData {
        private int myTotalKillCount;
        private int myKillCount;
        private int myAssistCount;
        private int myKillInvolvementInEnemyCamp;
        private int oppositeKillCount;
        private int oppositeAssistCount;
        private int oppositeKillInvolvementInEnemyCamp;
    }

    @Data
    public static class BottomData {
        private int myDuoKillCount;
        private long mySupportItemFinishedTime;
        private int oppositeDuoKillCount;
        private long oppositeSupportItemFinishedTime;
    }
}