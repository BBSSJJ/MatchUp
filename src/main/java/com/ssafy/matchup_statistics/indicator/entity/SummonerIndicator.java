package com.ssafy.matchup_statistics.indicator.entity;

import com.ssafy.matchup_statistics.indicator.entity.riot.league.LeagueIndicator;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.MatchIndicator;
import com.ssafy.matchup_statistics.indicator.entity.user.UserIndicator;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "summoner_indicators")
public class SummonerIndicator {
    @Id
    private Long summonerId;

    private MatchIndicator matchIndicator;
    private LeagueIndicator leagueIndicator;
    private UserIndicator userIndicator;
}
