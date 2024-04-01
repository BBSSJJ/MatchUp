package com.ssafy.matchup_statistics.indicator.entity.match.beginning;

import com.ssafy.matchup_statistics.indicator.entity.match.beginning.base.AggresiveLaneAbilility;
import com.ssafy.matchup_statistics.indicator.entity.match.beginning.base.BasicWeight;
import com.ssafy.matchup_statistics.indicator.entity.match.beginning.base.LaneAssist;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Getter
@NoArgsConstructor
@Slf4j
public class LaneIndicatorStatistics {

    private BasicWeight basicWeight;
    private AggresiveLaneAbilility aggresiveLaneAbilility;
    private LaneAssist laneAssist;

    public LaneIndicatorStatistics(List<LaneIndicator> laneIndicators) {
        List<BasicWeight> basicWeights = laneIndicators.stream().map(LaneIndicator::getBasicWeight).toList();
        List<AggresiveLaneAbilility> aggresiveLaneAbililities = laneIndicators.stream().map(LaneIndicator::getAggresiveLaneAbilility).toList();
        List<LaneAssist> laneAssists = laneIndicators.stream().map(
                laneIndicator -> (laneIndicator.getClass() == JgIndicator.class) ? ((JgIndicator) laneIndicator).getLaneAssist() : new LaneAssist()).toList();

        if (!basicWeights.isEmpty())
            basicWeight = new BasicWeight(basicWeights);
        if (!aggresiveLaneAbililities.isEmpty())
            aggresiveLaneAbilility = new AggresiveLaneAbilility(aggresiveLaneAbililities);
        if (!laneAssists.isEmpty())
            laneAssist = new LaneAssist(laneAssists);
    }
}
