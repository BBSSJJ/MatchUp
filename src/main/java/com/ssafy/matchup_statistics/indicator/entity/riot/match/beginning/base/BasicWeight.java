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
    private long supportItemFinishedTimeDiffer;

    public void setBasicWeightByData_15(LaneInfo laneInfo, MatchTimelineResponseDto.ParticipantNumber myData, MatchTimelineResponseDto.ParticipantNumber oppositeData) {
        expDiffer = myData.getXp() - oppositeData.getXp();

        // 정글은 cs 계산 시 정글몬스터만 확인
        if (laneInfo.getTeamPosition().equals(TeamPosition.JUNGLE))
            csDiffer = myData.getJungleMinionsKilled() - oppositeData.getJungleMinionsKilled();
        else csDiffer = myData.getMinionsKilled() - oppositeData.getMinionsKilled();
    }

    public void setBasicWeightByDataBefore_15(Before_15_Data before15Data) {
        towerGoldDiffer = before15Data.getCommonData().getMyTurretPlateDestroyCount() - before15Data.getCommonData().getOppositeTurretPlateDestroyCount();
        supportItemFinishedTimeDiffer = before15Data.getBottomData().getMySupportItemFinishedTime() - before15Data.getBottomData().getOppositeSupportItemFinishedTime();
    }
}
