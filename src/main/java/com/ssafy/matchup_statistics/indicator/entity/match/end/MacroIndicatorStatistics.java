package com.ssafy.matchup_statistics.indicator.entity.match.end;

import com.ssafy.matchup_statistics.indicator.entity.match.end.base.*;
import lombok.Getter;

import java.util.List;

@Getter
public class MacroIndicatorStatistics {
    private SplitPoint splitPoint;
    private InitiatingPoint initiatingPoint;
    private JungleHoldPoint jungleHoldPoint;
    private ObjectivePoint objectivePoint;
    private VisionPoint visionPoint;
    private TotalDealPoint totalDealPoint;
    public MacroIndicatorStatistics(List<MacroIndicator> macroIndicators) {
        List<SplitPoint> splitPoints = macroIndicators.stream().map(MacroIndicator::getSplitPoint).toList();
        List<InitiatingPoint> initiatingPoints = macroIndicators.stream().map(MacroIndicator::getInitiatingPoint).toList();
        List<JungleHoldPoint> jungleHoldPoints = macroIndicators.stream().map(MacroIndicator::getJungleHoldPoint).toList();
        List<ObjectivePoint> objectivePoints = macroIndicators.stream().map(MacroIndicator::getObjectivePoint).toList();
        List<VisionPoint> visionPoints = macroIndicators.stream().map(MacroIndicator::getVisionPoint).toList();
        List<TotalDealPoint> totalDealPoints = macroIndicators.stream().map(MacroIndicator::getTotalDealPoint).toList();

        splitPoint = new SplitPoint(splitPoints);
        initiatingPoint = new InitiatingPoint(initiatingPoints);
        jungleHoldPoint = new JungleHoldPoint(jungleHoldPoints);
        objectivePoint = new ObjectivePoint(objectivePoints);
        visionPoint = new VisionPoint(visionPoints);
        totalDealPoint = new TotalDealPoint(totalDealPoints);
    }
}
