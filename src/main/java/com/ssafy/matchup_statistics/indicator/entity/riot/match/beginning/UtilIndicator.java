package com.ssafy.matchup_statistics.indicator.entity.riot.match.beginning;

import com.ssafy.matchup_statistics.global.dto.response.MatchTimelineResponseDto;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.LaneInfo;

public class UtilIndicator extends LaneIndicator {
    public UtilIndicator(LaneInfo laneInfo, MatchTimelineResponseDto matchTimelineResponseDto) {
        super(laneInfo, matchTimelineResponseDto);
    }
}
