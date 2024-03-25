package com.ssafy.matchup_statistics.indicator.entity.match.end;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.matchup_statistics.indicator.data.MacroData;
import com.ssafy.matchup_statistics.indicator.entity.match.end.base.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@NoArgsConstructor
@Slf4j
public class MacroIndicator {

    private SplitPoint splitPoint = new SplitPoint();
    private InitiatingPoint initiatingPoint = new InitiatingPoint();
    private JungleHoldPoint jungleHoldPoint = new JungleHoldPoint();
    private ObjectivePoint objectivePoint = new ObjectivePoint();
    private VisionPoint visionPoint = new VisionPoint();
    private TotalDealPoint totalDealPoint = new TotalDealPoint();
    private final int DEFAULT_ROUND_UP = 100_000;

    public MacroIndicator(MacroData macroData) {

        // 운영정보와 매치 dto로부터 각 지표 생성
        log.debug("splitPoint 생성");
        splitPoint = new SplitPoint(macroData, DEFAULT_ROUND_UP);
        log.debug("initiatingPoint 생성");
        initiatingPoint = new InitiatingPoint(macroData, DEFAULT_ROUND_UP);
        log.debug("jungleHoldPoint 생성");
        jungleHoldPoint = new JungleHoldPoint(macroData, DEFAULT_ROUND_UP);
        log.debug("objectivePoint 생성");
        objectivePoint = new ObjectivePoint(macroData, DEFAULT_ROUND_UP);
        log.debug("visionPoint 생성");
        visionPoint = new VisionPoint(macroData, DEFAULT_ROUND_UP);
        log.debug("totalDealPoint 생성");
        totalDealPoint = new TotalDealPoint(macroData, DEFAULT_ROUND_UP);
    }
}
