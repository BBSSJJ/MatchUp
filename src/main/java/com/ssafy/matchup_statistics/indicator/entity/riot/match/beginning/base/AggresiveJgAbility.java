package com.ssafy.matchup_statistics.indicator.entity.riot.match.beginning.base;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AggresiveJgAbility extends AggresiveLaneAbilility{
    private int killInvolvementInEnemyCamp;
    @Override
    public void setAggresiveLaneAbililityByDataBefore_15(Before_15_Data before15Data) {
        this.killInvolvementInEnemyCamp = before15Data.getJgData().getMyKillInvolvementInEnemyCamp() - before15Data.getJgData().getOppositeKillInvolvementInEnemyCamp();
    }
}
