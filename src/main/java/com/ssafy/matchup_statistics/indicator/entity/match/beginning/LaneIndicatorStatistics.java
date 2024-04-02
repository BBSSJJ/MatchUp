package com.ssafy.matchup_statistics.indicator.entity.match.beginning;

import com.ssafy.matchup_statistics.indicator.entity.match.beginning.base.AggresiveLaneAbility;
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
    private AggresiveLaneAbility aggresiveLaneAbility;
    private LaneAssist laneAssist;

    public LaneIndicatorStatistics(List<LaneIndicator> laneIndicators) {
        List<BasicWeight> basicWeights = laneIndicators.stream().map(LaneIndicator::getBasicWeight).toList();
        List<AggresiveLaneAbility> aggresiveLaneAbilities = laneIndicators.stream().map(LaneIndicator::getAggresiveLaneAbility).toList();
        List<LaneAssist> laneAssists = laneIndicators.stream().map(
                laneIndicator -> (laneIndicator.getClass() == JgIndicator.class) ? ((JgIndicator) laneIndicator).getLaneAssist() : new LaneAssist()).toList();


        if (!basicWeights.isEmpty()) {
            log.debug("basic weight size : {}", basicWeights.size());
            basicWeight = new BasicWeight(basicWeights);
        }
        if (!aggresiveLaneAbilities.isEmpty()) {
            log.debug("basic weight size : {}", basicWeights.size());
            aggresiveLaneAbility = new AggresiveLaneAbility(aggresiveLaneAbilities);
        }
        if (!laneAssists.isEmpty()) {
            log.debug("basic weight size : {}", basicWeights.size());
            laneAssist = new LaneAssist(laneAssists);
        }
    }
}
