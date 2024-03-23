package com.ssafy.matchup_statistics.indicator.entity.match.beginning.base;

import com.ssafy.matchup_statistics.indicator.data.Before_15_Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@Getter
public class AggresiveJgAbility extends AggresiveLaneAbilility{
    private int killInvolvementInEnemyCamp;
    @Override
    public void setAggresiveLaneAbililityByDataBefore_15(Before_15_Data before15Data) {
        this.killInvolvementInEnemyCamp = before15Data.getJgData().getMyKillInvolvementInEnemyCamp() - before15Data.getJgData().getOppositeKillInvolvementInEnemyCamp();
    }
}
