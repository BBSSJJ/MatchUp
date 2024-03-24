package com.ssafy.matchup_statistics.indicator.entity.match.end;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.matchup_statistics.indicator.data.MacroData;
import com.ssafy.matchup_statistics.indicator.entity.match.end.base.*;
import lombok.Getter;

@Getter
public class MacroIndicator {

    private SplitPoint splitPoint;
    private InitiatingPoint initiatingPoint;
    private JungleHoldPoint jungleHoldPoint;
    private ObjectivePoint objectivePoint;
    private VisionPoint visionPoint;
    private TotalDealPoint totalDealPoint;
    private final int DEFAULT_ROUND_UP = 100_000;

    public MacroIndicator(MacroData macroData) {

        // 운영정보와 매치 dto로부터 각 지표 생성
        splitPoint = new SplitPoint(macroData, DEFAULT_ROUND_UP);
        initiatingPoint = new InitiatingPoint(macroData, DEFAULT_ROUND_UP);
        jungleHoldPoint = new JungleHoldPoint(macroData, DEFAULT_ROUND_UP);
        objectivePoint = new ObjectivePoint(macroData, DEFAULT_ROUND_UP);
        visionPoint = new VisionPoint(macroData, DEFAULT_ROUND_UP);
        totalDealPoint = new TotalDealPoint(macroData, DEFAULT_ROUND_UP);
    }
}
