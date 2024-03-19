package com.ssafy.matchup_statistics.indicator.entity.riot.match.beginning;

import com.ssafy.matchup_statistics.match.entity.Match;
import lombok.Data;

import java.util.List;

@Data
public class LaneIndicator {

    private List<String> calculatedTopMatches;
    private DetailLaneIndicator totalTopIndicator;
    private List<String> calculatedJgMatchIds;
    private DetailLaneIndicator totalJgIndicator;
    private List<String> calculatedMidMatchIds;
    private DetailLaneIndicator totalMidIndicator;
    private List<String> calculatedAdcMatchIds;
    private DetailLaneIndicator totalAdcIndicator;
    private List<String> calculatedSupMatchIds;
    private DetailLaneIndicator totalSupIndicator;
    private List<String> calculatedAllMatchIds;

}
