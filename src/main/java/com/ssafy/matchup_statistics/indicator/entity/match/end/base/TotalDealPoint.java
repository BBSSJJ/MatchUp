package com.ssafy.matchup_statistics.indicator.entity.match.end.base;

import com.ssafy.matchup_statistics.indicator.data.MacroData;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
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

    public TotalDealPoint(List<TotalDealPoint> totalDealPoints) {
        totalDealPoints.forEach(totalDealPoint -> {
            damagePerMinute += totalDealPoint.getDamagePerMinute();
            dealPerGold += totalDealPoint.getDealPerGold();
            teamDamagePercentage += totalDealPoint.getTeamDamagePercentage();
        });
        damagePerMinute /= totalDealPoints.size();
        dealPerGold /= totalDealPoints.size();
        teamDamagePercentage /= totalDealPoints.size();
    }
}
