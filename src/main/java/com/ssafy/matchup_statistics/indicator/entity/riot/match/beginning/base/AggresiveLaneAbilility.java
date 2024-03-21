package com.ssafy.matchup_statistics.indicator.entity.riot.match.beginning.base;

import com.ssafy.matchup_statistics.match.api.dto.response.MatchTimelineResponseDto;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AggresiveLaneAbilility {
    private int dealDiffer;
    private int soloKillDiffer;

    public void setAggresiveLaneAbililityByData_15(MatchTimelineResponseDto.ParticipantNumber myData, MatchTimelineResponseDto.ParticipantNumber oppositeData) {
        this.dealDiffer = myData.damageStats.totalDamageDoneToChampions - oppositeData.damageStats.totalDamageDoneToChampions;
    }

    public void setAggresiveLaneAbililityByDataBefore_15(Before_15_Data before15Data) {
        this.soloKillDiffer = before15Data.getTopMidData().getMySoloKillCount() - before15Data.getTopMidData().getOppositeSoloKillCount();
    }
}
