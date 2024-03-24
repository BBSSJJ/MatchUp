package com.ssafy.matchup_statistics.indicator.entity.match.end.base;

import com.ssafy.matchup_statistics.indicator.data.MacroData;
import lombok.Getter;

import java.util.List;

@Getter
public class JungleHoldPoint {
    private long totalJungleObjectivePerGameDuration;

    public JungleHoldPoint(MacroData marcoTeamData, int DEFAULT_ROUND_UP) {
        totalJungleObjectivePerGameDuration =
                ((long) marcoTeamData.getMyData().getTotalEnemyJungleMinionsKilled() * DEFAULT_ROUND_UP / marcoTeamData.getGameDuration());
    }

    public JungleHoldPoint(List<JungleHoldPoint> jungleHoldPoints) {
        if (jungleHoldPoints.isEmpty()) return;
        jungleHoldPoints.forEach(jungleHoldPoint -> {
            totalJungleObjectivePerGameDuration += jungleHoldPoint.getTotalJungleObjectivePerGameDuration();
        });
        totalJungleObjectivePerGameDuration /= jungleHoldPoints.size();
    }
}