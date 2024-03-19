package com.ssafy.matchup_statistics.indicator.entity.riot;

import com.ssafy.matchup_statistics.indicator.entity.riot.league.LeagueIndicator;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.MatchIndicator;

public class RiotIndicator {
    private LeagueIndicator leagueIndicator;
    private MatchIndicator matchIndicator;

    public RiotIndicator(LeagueIndicator leagueIndicator, MatchIndicator matchIndicator) {
        this.leagueIndicator = leagueIndicator;
        this.matchIndicator = matchIndicator;
    }
}
