package com.ssafy.matchup_statistics.indicator.entity.riot.match.beginning;

import com.ssafy.matchup_statistics.indicator.entity.riot.match.LaneInfo;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.beginning.base.Before_15_Data;
import com.ssafy.matchup_statistics.match.api.dto.response.MatchTimelineResponseDto;

public class UtilIndicator extends LaneIndicator {
    public UtilIndicator(LaneInfo laneInfo, MatchTimelineResponseDto matchTimelineResponseDto) {
        super(laneInfo, matchTimelineResponseDto);
    }
}
