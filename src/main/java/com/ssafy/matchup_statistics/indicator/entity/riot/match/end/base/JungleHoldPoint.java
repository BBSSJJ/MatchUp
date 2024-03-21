package com.ssafy.matchup_statistics.indicator.entity.riot.match.end.base;

import com.ssafy.matchup_statistics.indicator.entity.riot.match.end.MacroData;
import lombok.Data;

@Data
public class JungleHoldPoint {
    private int totalJungleObjectivePerGameDuration;
    private final int DEFAULT_ROUND_UP = 100_000;

    public JungleHoldPoint(MacroData marcoTeamData) {
        totalJungleObjectivePerGameDuration =
                (int) (marcoTeamData.getMyData().getTotalEnemyJungleMinionsKilled() * DEFAULT_ROUND_UP / marcoTeamData.getGameDuration());
    }
}