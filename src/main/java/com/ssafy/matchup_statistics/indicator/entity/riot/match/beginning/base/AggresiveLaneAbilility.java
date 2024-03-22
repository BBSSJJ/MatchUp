package com.ssafy.matchup_statistics.indicator.entity.riot.match.beginning.base;

import com.ssafy.matchup_statistics.global.dto.response.MatchTimelineResponseDto;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AggresiveLaneAbilility {
    private int dealDiffer;
    private int soloKillDiffer;
    private int duoKillDiffer;

    public void setAggresiveLaneAbililityByData_15(MatchTimelineResponseDto.ParticipantNumber myData, MatchTimelineResponseDto.ParticipantNumber oppositeData) {
        dealDiffer = myData.damageStats.totalDamageDoneToChampions - oppositeData.damageStats.totalDamageDoneToChampions;
    }

    public void setAggresiveLaneAbililityByDataBefore_15(Before_15_Data before15Data) {
        soloKillDiffer = before15Data.getCommonData().getMySoloKillCount() - before15Data.getCommonData().getOppositeSoloKillCount();
        duoKillDiffer = before15Data.getBottomData().getMyDuoKillCount() - before15Data.getBottomData().getOppositeDuoKillCount();
    }
}
