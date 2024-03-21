package com.ssafy.matchup_statistics.indicator.entity.riot.match.end.base;

import com.ssafy.matchup_statistics.indicator.entity.riot.match.end.MacroData;
import lombok.Data;

@Data
public class VisionPoint {
    private long visionScorePerDeath;
    private final int DEFAULT_ROUND_UP = 100_000;

    public VisionPoint(MacroData macroData) {
        visionScorePerDeath = (long) macroData.getMyData().getVisionScore() * DEFAULT_ROUND_UP / (macroData.getMyData().getDeaths() + 1);
    }
}
