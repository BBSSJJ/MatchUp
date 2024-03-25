package com.ssafy.matchup_statistics.indicator.entity.match.end.base;

import com.ssafy.matchup_statistics.indicator.data.MacroData;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class TotalDealPoint {
    private long damagePerMinute;
    private long dealPerGold;
    private double teamDamagePercentage;

    public TotalDealPoint(MacroData macroData, int DEFAULT_ROUND_UP) {
        damagePerMinute = (long) macroData.getMyData().challenges.getDamagePerMinute() * DEFAULT_ROUND_UP;
        dealPerGold = (long) ((long) macroData.getMyData().challenges.getDamagePerMinute() * DEFAULT_ROUND_UP
                                / (macroData.getMyData().challenges.getGoldPerMinute() + 1));
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
