package com.ssafy.matchup_statistics.indicator.entity.riot.match;

import com.ssafy.matchup_statistics.indicator.entity.riot.match.beginning.LaneIndicator;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.end.EtcIndicator;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.end.MacroIndicator;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class MatchIndicator {
    private Boolean isBottomLane;
    private Integer myLaneNumber;
    private Integer oppositeLaneNumber;
    private int[] bottomDuoLaneNumbers;

    private LaneIndicator laneIndicator;
    private MacroIndicator macroIndicator;
    private EtcIndicator etcIndicator;
}
