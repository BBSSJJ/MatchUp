package com.ssafy.matchup_statistics.indicator.entity.match.beginning;

import com.ssafy.matchup_statistics.global.dto.response.MatchTimelineResponseDto;
import com.ssafy.matchup_statistics.indicator.entity.match.LaneInfo;

public class TopIndicator extends LaneIndicator {

    public TopIndicator(LaneInfo laneInfo, MatchTimelineResponseDto matchTimelineResponseDto) {
        super(laneInfo, matchTimelineResponseDto);
    }
}
