package com.ssafy.matchup_statistics.indicator.entity.riot.match.end.base;

import com.ssafy.matchup_statistics.indicator.entity.riot.match.end.MacroData;
import lombok.Data;

@Data
public class TotalDealPoint {
    private long damagePerMinute;
    private long dealPerGold;
    private double teamDamagePercentage;
    private final int DEFAULT_ROUND_UP = 100_000;

    public TotalDealPoint(MacroData macroData) {
        damagePerMinute = (long) macroData.getMyData().challenges.getDamagePerMinute() * DEFAULT_ROUND_UP;
        dealPerGold = (long) ((long) macroData.getMyData().challenges.getDamagePerMinute() * DEFAULT_ROUND_UP
                                / macroData.getMyData().challenges.getGoldPerMinute());
        teamDamagePercentage = macroData.getMyData().challenges.getTeamDamagePercentage();
    }
}
