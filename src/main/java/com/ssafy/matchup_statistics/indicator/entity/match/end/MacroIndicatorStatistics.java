package com.ssafy.matchup_statistics.indicator.entity.match.end;

import com.ssafy.matchup_statistics.indicator.entity.match.end.base.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Getter
@NoArgsConstructor
@Slf4j
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

        log.debug("splitPoint 생성, splitPoints : {}", splitPoints);
        if (!splitPoints.isEmpty()) {
            splitPoint = new SplitPoint(splitPoints);
        }

        log.debug("initiatingPoints 생성, initiatingPoints : {}", initiatingPoints);
        if (!initiatingPoints.isEmpty()) {
            initiatingPoint = new InitiatingPoint(initiatingPoints);
        }

        log.debug("jungleHoldPoints 생성, jungleHoldPoints : {}", jungleHoldPoints);
        if (!jungleHoldPoints.isEmpty()) {
            jungleHoldPoint = new JungleHoldPoint(jungleHoldPoints);
        }

        log.debug("objectivePoint 생성, objectivePoints : {}", objectivePoints);
        if (!objectivePoints.isEmpty()) {
            objectivePoint = new ObjectivePoint(objectivePoints);
        }

        log.debug("visionPoints 생성, visionPoints : {}", visionPoints);
        if (!visionPoints.isEmpty()) {
            visionPoint = new VisionPoint(visionPoints);
        }

        log.debug("totalDealPoints 생성, totalDealPoints : {}", totalDealPoints);
        if (!totalDealPoints.isEmpty())
            totalDealPoint = new TotalDealPoint(totalDealPoints);
    }
}
