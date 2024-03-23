package com.ssafy.matchup_statistics.indicator.entity.match.beginning.base;

import com.ssafy.matchup_statistics.global.dto.response.MatchTimelineResponseDto;
import com.ssafy.matchup_statistics.indicator.data.Before_15_Data;
import com.ssafy.matchup_statistics.indicator.entity.match.TeamPosition;
import com.ssafy.matchup_statistics.indicator.entity.match.LaneInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class BasicWeight {
    private int csDiffer;
    private int expDiffer;
    private double turretPlateDestroyDiffer;
    private long supportItemFinishedTimeDiffer;

    public BasicWeight(List<BasicWeight> basicWeights) {
        basicWeights.forEach(basicWeight -> {
            csDiffer += basicWeight.getCsDiffer();
            expDiffer += basicWeight.getExpDiffer();
            turretPlateDestroyDiffer += basicWeight.getTurretPlateDestroyDiffer();
            supportItemFinishedTimeDiffer += basicWeight.getSupportItemFinishedTimeDiffer();
        });
        csDiffer /= basicWeights.size();
        expDiffer /= basicWeights.size();
        turretPlateDestroyDiffer /= basicWeights.size();
        supportItemFinishedTimeDiffer /= basicWeights.size();
    }

    public void setBasicWeightByData_15(LaneInfo laneInfo, MatchTimelineResponseDto.ParticipantNumber myData, MatchTimelineResponseDto.ParticipantNumber oppositeData) {
        expDiffer = myData.getXp() - oppositeData.getXp();

        // 정글은 cs 계산 시 정글몬스터만 확인
        if (laneInfo.getTeamPosition().equals(TeamPosition.JUNGLE))
            csDiffer = myData.getJungleMinionsKilled() - oppositeData.getJungleMinionsKilled();
        else csDiffer = myData.getMinionsKilled() - oppositeData.getMinionsKilled();
    }

    public void setBasicWeightByDataBefore_15(Before_15_Data before15Data) {
        turretPlateDestroyDiffer = before15Data.getCommonData().getMyTurretPlateDestroyCount() - before15Data.getCommonData().getOppositeTurretPlateDestroyCount();
        supportItemFinishedTimeDiffer = before15Data.getBottomData().getMySupportItemFinishedTime() - before15Data.getBottomData().getOppositeSupportItemFinishedTime();
    }
}
