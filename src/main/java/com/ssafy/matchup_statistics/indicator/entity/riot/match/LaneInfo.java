package com.ssafy.matchup_statistics.indicator.entity.riot.match;

import com.ssafy.matchup_statistics.match.api.dto.response.MatchDetailResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
@AllArgsConstructor
@Slf4j
public class LaneInfo {
    private TeamPosition teamPosition;
    private boolean isBottomLane;
    private int myTeamId;
    private int myLaneNumber;
    private int oppositeLaneNumber;
    private int myBottomDuoNumber;
    private int oppositeBottomDuoNumber;

    public LaneInfo(String puuid, MatchDetailResponseDto matchDetailResponseDto) {
        log.debug("puuid : " + puuid);
        matchDetailResponseDto.getInfo().participants.forEach(participant -> {
            // 내 라인정보 저장
            if (participant.puuid.equals(puuid)) {
                this.teamPosition = TeamPosition.valueOf(participant.teamPosition);
                this.isBottomLane = this.teamPosition.equals(TeamPosition.BOTTOM) || this.teamPosition.equals(TeamPosition.UTILITY);
                this.myLaneNumber = participant.participantId;
                this.myTeamId = participant.teamId;
            }
        });

        // 상대 라인정보 및 바텀 라인정보 저장
        matchDetailResponseDto.getInfo().participants.forEach(participant -> {
            if (participant.puuid.equals(puuid)) return;

            // 상대 라인정보 저장
            if (TeamPosition.valueOf(participant.teamPosition).equals(teamPosition)) {
                this.oppositeLaneNumber = participant.participantId;
            }

            // 바텀일때
            if (this.isBottomLane) {
                // 원딜-서폿 / 서폿-원딜일때 필드에 저장
                if (this.teamPosition.equals(TeamPosition.BOTTOM) && participant.teamPosition.equals("UTILITY")
                        || (this.teamPosition.equals(TeamPosition.UTILITY) && participant.teamPosition.equals("BOTTOM"))) {
                    if (participant.teamId == myTeamId) this.myBottomDuoNumber = participant.participantId;
                    else this.oppositeBottomDuoNumber = participant.participantId;
                }
                // 바텀 아닐때
            } else {
                this.myBottomDuoNumber = 0;
                this.oppositeBottomDuoNumber = 0;
            }
        });
    }
}
