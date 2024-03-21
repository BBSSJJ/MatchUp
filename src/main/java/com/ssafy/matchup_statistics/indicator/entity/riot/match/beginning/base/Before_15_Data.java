package com.ssafy.matchup_statistics.indicator.entity.riot.match.beginning.base;


import com.ssafy.matchup_statistics.indicator.entity.riot.match.LaneInfo;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.LaneType;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.TeamPosition;
import com.ssafy.matchup_statistics.match.api.dto.response.MatchTimelineResponseDto;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Data
@Slf4j
public class Before_15_Data {

    private TopMidData topMidData = new TopMidData();
    private JgData jgData = new JgData();

    public Before_15_Data(LaneInfo laneInfo, MatchTimelineResponseDto matchTimelineResponseDto) {
        int myLaneNumber = laneInfo.getMyLaneNumber();
        int oppositeLaneNumber = laneInfo.getOppositeLaneNumber();
        int myTeamId = laneInfo.getMyTeamId();
        TeamPosition myTeamPosition = laneInfo.getTeamPosition();

        for (int minute = 0; minute <= 15; minute++) {
            MatchTimelineResponseDto.Frame frame = matchTimelineResponseDto.getInfo().getFrames().get(minute);
            frame.getEvents().forEach(event -> {

                // 탑, 미드
                if (myTeamPosition.equals(TeamPosition.TOP) || myTeamPosition.equals(TeamPosition.MIDDLE)) {
                    switch (event.type) {
                        case "TURRET_PLATE_DESTROYED": {
                            this.countTurretPlateDestroy(myTeamId, myTeamPosition, event);
                            break;
                        }
                        case "CHAMPION_KILL": {
                            this.countSoloKill(myLaneNumber, oppositeLaneNumber, event);
                            break;
                        }
                    }
                }

                // 정글
                else if (myTeamPosition.equals(TeamPosition.JUNGLE)) {
                    switch (event.type) {
                        case "CHAMPION_KILL": {
                            if (judgeIsOurKill(myTeamId, event.killerId)) addMyKillCount(laneInfo, event);
                            else addOppositeKillCount(laneInfo, event);
                            break;
                        }
                    }
                }
            });
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

    public void countTurretPlateDestroy(int myTeamId, TeamPosition myTeamPosition,
                                        MatchTimelineResponseDto.Event event) {
        if (myTeamPosition.equals(TeamPosition.JUNGLE)) return;
        LaneType myLaneType = myTeamPosition.parseLaneType();
        if (event.laneType.equals(myLaneType.toString())) {
            if (event.teamId == myTeamId) topMidData.myTurretPlateDestroyCount++;
            else topMidData.oppositeTurretPlateDestroyCount++;
        }
    }

    public void countSoloKill(int myLaneNumber, int oppositeLaneNumber, MatchTimelineResponseDto.Event event) {
        if (event.assistingParticipantIds == null)
            if (event.killerId == myLaneNumber && event.victimId == oppositeLaneNumber)
                topMidData.mySoloKillCount++;
            else if (event.killerId == oppositeLaneNumber && event.victimId == myLaneNumber)
                topMidData.oppositeSoloKillCount++;
    }

    @Data
    public static class TopMidData {
        private int myTurretPlateDestroyCount;
        private int oppositeTurretPlateDestroyCount;
        private int mySoloKillCount;
        private int oppositeSoloKillCount;

    }

    @Data
    public static class JgData {
        private int myKillInvolvementInEnemyCamp;
        private int myTotalKillCount;
        private int myKillCount;
        private int myAssistCount;
        private int oppositeKillInvolvementInEnemyCamp;
        private int oppositeKillCount;
        private int oppositeAssistCount;
    }
}