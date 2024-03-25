package com.ssafy.matchup_statistics.indicator.entity.match.end.base;

import com.ssafy.matchup_statistics.indicator.data.MacroData;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class JungleHoldPoint {
    private long totalJungleObjectivePerGameDuration;

    public JungleHoldPoint(MacroData marcoTeamData, int DEFAULT_ROUND_UP) {
        totalJungleObjectivePerGameDuration =
                ((long) marcoTeamData.getMyData().getTotalEnemyJungleMinionsKilled() * DEFAULT_ROUND_UP / (marcoTeamData.getGameDuration() + 1));
    }

    public JungleHoldPoint(List<JungleHoldPoint> jungleHoldPoints) {
        jungleHoldPoints.forEach(jungleHoldPoint -> {
            totalJungleObjectivePerGameDuration += jungleHoldPoint.getTotalJungleObjectivePerGameDuration();
        });
        totalJungleObjectivePerGameDuration /= jungleHoldPoints.size();
    }
}