package com.ssafy.matchup_statistics.indicator.entity;

import com.ssafy.matchup_statistics.indicator.entity.riot.league.LeagueIndicator;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.MatchIndicator;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "summoner_indicators")
@ToString
public class SummonerIndicator {
    @Id
    private String summonerId;

    private final List<MatchIndicator> matchIndicators;
    private final LeagueIndicator leagueIndicator;

    public SummonerIndicator(String summonerId,
                             List<MatchIndicator> matchIndicators,
                             LeagueIndicator leagueIndicator) {
        this.summonerId = summonerId;
        this.matchIndicators = matchIndicators;
        this.leagueIndicator = leagueIndicator;
    }
}
