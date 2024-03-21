package com.ssafy.matchup_statistics.indicator.entity.riot;

import com.ssafy.matchup_statistics.indicator.entity.riot.league.LeagueIndicator;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.MatchIndicator;
import com.ssafy.matchup_statistics.match.api.dto.response.MatchDetailResponseDto;
import com.ssafy.matchup_statistics.match.api.dto.response.MatchTimelineResponseDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class RiotIndicator {
    private LeagueIndicator leagueIndicator;
    private List<MatchIndicator> matchIndicators;

    public RiotIndicator(LinkedHashMap<String, Object> leagueInfo, List<MatchIndicator> matchIndicators) {
        this.leagueIndicator = new LeagueIndicator(leagueInfo);
        this.matchIndicators = matchIndicators;
    }
}
