package com.ssafy.matchup_statistics.indicator.entity.match;

import com.ssafy.matchup_statistics.indicator.entity.match.beginning.LaneIndicator;
import com.ssafy.matchup_statistics.indicator.entity.match.beginning.LaneIndicatorStatistics;
import com.ssafy.matchup_statistics.indicator.entity.match.end.MacroIndicator;
import com.ssafy.matchup_statistics.indicator.entity.match.end.MacroIndicatorStatistics;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Getter
@Slf4j
public class MatchIndicatorStatistics {

    private String matchId;
    private LaneIndicatorStatistics laneIndicator;
    private MacroIndicatorStatistics macroIndicator;

    public MatchIndicatorStatistics(List<MatchIndicator> matchIndicators) {
        List<LaneIndicator> laneIndicators = matchIndicators.stream().map(MatchIndicator::getLaneIndicator).toList();
        List<MacroIndicator> macroIndicators = matchIndicators.stream().map(MatchIndicator::getMacroIndicator).toList();
        log.debug("라인통계 평균 생성시작");
        if (!laneIndicators.isEmpty()) this.laneIndicator = new LaneIndicatorStatistics(laneIndicators);
        log.debug("운영통계 평균 생성시작");
        if (!macroIndicators.isEmpty()) this.macroIndicator = new MacroIndicatorStatistics(macroIndicators);
    }

}
