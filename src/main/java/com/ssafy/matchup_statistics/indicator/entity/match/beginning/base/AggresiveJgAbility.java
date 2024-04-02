package com.ssafy.matchup_statistics.indicator.entity.match.beginning.base;

import com.ssafy.matchup_statistics.indicator.data.Before_15_Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Getter
public class AggresiveJgAbility extends AggresiveLaneAbility {
    private int killInvolvementInEnemyCamp;
    @Override
    public void setAggresiveLaneAbilityByDataBefore_15(Before_15_Data before15Data) {
        this.killInvolvementInEnemyCamp = before15Data.getJgData().getMyKillInvolvementInEnemyCamp() - before15Data.getJgData().getOppositeKillInvolvementInEnemyCamp();
    }
}
