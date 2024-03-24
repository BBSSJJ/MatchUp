package com.ssafy.matchup_statistics.indicator.entity.match.beginning.base;

import com.ssafy.matchup_statistics.global.dto.response.MatchTimelineResponseDto;
import com.ssafy.matchup_statistics.indicator.data.Before_15_Data;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AggresiveLaneAbilility {
    private int dealDiffer;
    private int soloKillDiffer;
    private int duoKillDiffer;

    public AggresiveLaneAbilility(List<AggresiveLaneAbilility> aggresiveLaneAbililities) {
        if (aggresiveLaneAbililities.isEmpty()) return;
        aggresiveLaneAbililities.forEach(aggresiveLaneAbilility -> {
            dealDiffer += aggresiveLaneAbilility.getDealDiffer();
            soloKillDiffer += aggresiveLaneAbilility.getSoloKillDiffer();
            duoKillDiffer += aggresiveLaneAbilility.getDuoKillDiffer();
        });
        dealDiffer /= aggresiveLaneAbililities.size();
        duoKillDiffer /= aggresiveLaneAbililities.size();
        soloKillDiffer /= aggresiveLaneAbililities.size();
    }

    public void setAggresiveLaneAbililityByData_15(MatchTimelineResponseDto.ParticipantNumber myData, MatchTimelineResponseDto.ParticipantNumber oppositeData) {
        dealDiffer = myData.damageStats.totalDamageDoneToChampions - oppositeData.damageStats.totalDamageDoneToChampions;
    }

    public void setAggresiveLaneAbililityByDataBefore_15(Before_15_Data before15Data) {
        soloKillDiffer = before15Data.getCommonData().getMySoloKillCount() - before15Data.getCommonData().getOppositeSoloKillCount();
        duoKillDiffer = before15Data.getBottomData().getMyDuoKillCount() - before15Data.getBottomData().getOppositeDuoKillCount();
    }
}
