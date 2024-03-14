package com.ssafy.matchup_statistics.indicator.entity.riot.match;

import com.ssafy.matchup_statistics.indicator.entity.riot.match.base.beginning.AggresiveLaneAbilility;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.base.beginning.BasicWeight;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.base.beginning.LaneAssist;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.base.beginning.LaneLead;

public class LaneIndicator {
    private LaneLead laneLead;
    private BasicWeight basicWeight;
    private AggresiveLaneAbilility aggresiveLaneAbilility;
    private LaneAssist laneAssist;
}
