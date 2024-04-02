package com.ssafy.matchup_statistics.indicator.entity.match.beginning.base;

import com.ssafy.matchup_statistics.global.dto.response.MatchTimelineResponseDto;
import com.ssafy.matchup_statistics.indicator.data.Before_15_Data;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AggresiveLaneAbility {
    private int dealDiffer;
    private int soloKillDiffer;
    private int duoKillDiffer;

    public AggresiveLaneAbility(List<AggresiveLaneAbility> aggresiveLaneAbilities) {
        aggresiveLaneAbilities.forEach(aggresiveLaneAbility -> {
            dealDiffer += aggresiveLaneAbility.getDealDiffer();
            soloKillDiffer += aggresiveLaneAbility.getSoloKillDiffer();
            duoKillDiffer += aggresiveLaneAbility.getDuoKillDiffer();
        });
        dealDiffer /= aggresiveLaneAbilities.size();
        duoKillDiffer /= aggresiveLaneAbilities.size();
        soloKillDiffer /= aggresiveLaneAbilities.size();
    }

    public void setAggresiveLaneAbilityByData_15(MatchTimelineResponseDto.ParticipantNumber myData, MatchTimelineResponseDto.ParticipantNumber oppositeData) {
        dealDiffer = myData.damageStats.totalDamageDoneToChampions - oppositeData.damageStats.totalDamageDoneToChampions;
    }

    public void setAggresiveLaneAbilityByDataBefore_15(Before_15_Data before15Data) {
        soloKillDiffer = before15Data.getCommonData().getMySoloKillCount() - before15Data.getCommonData().getOppositeSoloKillCount();
        duoKillDiffer = before15Data.getBottomData().getMyDuoKillCount() - before15Data.getBottomData().getOppositeDuoKillCount();
    }
}
