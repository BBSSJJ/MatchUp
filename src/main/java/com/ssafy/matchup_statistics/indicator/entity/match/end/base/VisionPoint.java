package com.ssafy.matchup_statistics.indicator.entity.match.end.base;

import com.ssafy.matchup_statistics.indicator.data.MacroData;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
public class VisionPoint {
    private long visionScorePerDeath;
    private final int DEFAULT_ROUND_UP = 100_000;

    public VisionPoint(MacroData macroData) {
        visionScorePerDeath = (long) macroData.getMyData().getVisionScore() * DEFAULT_ROUND_UP / (macroData.getMyData().getDeaths() + 1);
    }

    public VisionPoint(List<VisionPoint> visionPoints) {
        visionPoints.forEach(visionPoint -> {
            visionScorePerDeath += visionPoint.getVisionScorePerDeath();
        });
        visionScorePerDeath /= visionPoints.size();
    }
}
