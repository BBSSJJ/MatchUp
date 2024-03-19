package com.ssafy.matchup_statistics.indicator.entity.riot.match.beginning;

import com.ssafy.matchup_statistics.indicator.entity.riot.match.beginning.base.*;
import lombok.Data;

@Data
public class DetailLaneIndicator {
    private Boolean isFinishedBeforeFifteen;
    private LaneType laneType;

    private BasicWeight basicWeight;
    private AggresiveLaneAbilility aggresiveLaneAbilility;
    private LaneAssist laneAssist;
}
