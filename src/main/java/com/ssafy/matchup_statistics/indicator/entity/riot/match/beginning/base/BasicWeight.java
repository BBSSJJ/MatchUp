package com.ssafy.matchup_statistics.indicator.entity.riot.match.beginning.base;

import com.ssafy.matchup_statistics.indicator.entity.riot.match.LaneInfo;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.TeamPosition;
import com.ssafy.matchup_statistics.match.api.dto.response.MatchTimelineResponseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BasicWeight {
    private int csDiffer;
    private int expDiffer;
    private int towerGoldDiffer;
    private int objectiveDiffer;
    private int supportItemFinishedTimeDiffer;

    public void setBasicWeightByData_15(LaneInfo laneInfo, MatchTimelineResponseDto.ParticipantNumber myData, MatchTimelineResponseDto.ParticipantNumber oppositeData) {
        this.expDiffer = myData.getXp() - oppositeData.getXp();

        // 정글은 정글몬스터만 확인
        if (laneInfo.getTeamPosition().equals(TeamPosition.JUNGLE))
            this.csDiffer = myData.getJungleMinionsKilled() - oppositeData.getJungleMinionsKilled();
        else this.csDiffer = myData.getMinionsKilled() - oppositeData.getMinionsKilled();
    }

    public void setBasicWeightByDataBefore_15(Before_15_Data before15Data) {
        this.towerGoldDiffer = before15Data.getTopMidData().getMyTurretPlateDestroyCount() - before15Data.getTopMidData().getOppositeTurretPlateDestroyCount();
    }
}
