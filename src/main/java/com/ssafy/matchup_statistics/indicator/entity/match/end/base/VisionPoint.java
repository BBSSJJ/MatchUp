package com.ssafy.matchup_statistics.indicator.entity.match.end.base;

import com.ssafy.matchup_statistics.indicator.data.MacroData;
import lombok.Getter;

import java.util.List;

@Getter
public class VisionPoint {
    private long visionScorePerDeath;

    public VisionPoint(MacroData macroData, int DEFAULT_ROUND_UP) {
        visionScorePerDeath = (long) macroData.getMyData().getVisionScore() * DEFAULT_ROUND_UP / (macroData.getMyData().getDeaths() + 1);
    }

    public VisionPoint(List<VisionPoint> visionPoints) {
        if (visionPoints.isEmpty()) return;

        visionPoints.forEach(visionPoint -> {
            visionScorePerDeath += visionPoint.getVisionScorePerDeath();
        });
        visionScorePerDeath /= visionPoints.size();
    }
}
