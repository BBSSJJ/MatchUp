package com.ssafy.matchup_statistics.indicator.entity.match.end;

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

    public MacroIndicator(MacroData macroData) {

        // 운영정보와 매치 dto로부터 각 지표 생성
        splitPoint = new SplitPoint(macroData);
        initiatingPoint = new InitiatingPoint(macroData);
        jungleHoldPoint = new JungleHoldPoint(macroData);
        objectivePoint = new ObjectivePoint(macroData);
        visionPoint = new VisionPoint(macroData);
        totalDealPoint = new TotalDealPoint(macroData);
    }
}
