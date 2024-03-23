package com.ssafy.matchup_statistics.indicator.entity.match;

import com.ssafy.matchup_statistics.indicator.entity.match.beginning.LaneIndicator;
import com.ssafy.matchup_statistics.indicator.entity.match.beginning.LaneIndicatorStatistics;
import com.ssafy.matchup_statistics.indicator.entity.match.end.MacroIndicator;
import com.ssafy.matchup_statistics.indicator.entity.match.end.MacroIndicatorStatistics;
import lombok.Getter;

import java.util.List;

@Getter
public class MatchIndicatorStatistics {

    private String matchId;
    private LaneIndicatorStatistics laneIndicator;
    private MacroIndicatorStatistics macroIndicator;

    public MatchIndicatorStatistics(List<MatchIndicator> matchIndicators) {
        List<LaneIndicator> laneIndicators = matchIndicators.stream().map(MatchIndicator::getLaneIndicator).toList();
        List<MacroIndicator> macroIndicators = matchIndicators.stream().map(MatchIndicator::getMacroIndicator).toList();
        this.laneIndicator = new LaneIndicatorStatistics(laneIndicators);
        this.macroIndicator = new MacroIndicatorStatistics(macroIndicators);
    }

}
