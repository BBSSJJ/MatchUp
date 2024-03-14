package com.ssafy.matchup_statistics.indicator.entity.riot;

import com.ssafy.matchup_statistics.indicator.entity.riot.match.EtcIndicator;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.LaneIndicator;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.MacroIndicator;
import com.ssafy.matchup_statistics.match.entity.Match;

import java.util.List;

public class MatchIndicator {
    private List<Match> calculatedTopMatches;
    private LaneIndicator totalTopIndicator;
    private List<Match> calculatedJgMatches;
    private LaneIndicator totalJgIndicator;
    private List<Match> calculatedMidMatches;
    private LaneIndicator totalMidIndicator;
    private List<Match> calculatedAdcMatches;
    private LaneIndicator totalAdcIndicator;
    private List<Match> calculatedSupMatches;
    private LaneIndicator totalSupIndicator;
    private List<Match> calculatedAllMatches;
    private MacroIndicator totalMacroIndicator;
    private EtcIndicator totalEtcIndicator;
}
