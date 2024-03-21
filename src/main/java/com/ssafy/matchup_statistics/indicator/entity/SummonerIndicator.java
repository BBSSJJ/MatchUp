package com.ssafy.matchup_statistics.indicator.entity;

import com.ssafy.matchup_statistics.indicator.entity.riot.league.LeagueIndicator;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.MatchIndicator;
import com.ssafy.matchup_statistics.indicator.entity.user.UserIndicator;
import com.ssafy.matchup_statistics.match.api.dto.response.MatchDetailResponseDto;
import com.ssafy.matchup_statistics.match.api.dto.response.MatchTimelineResponseDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedHashMap;
import java.util.List;

@Document(collection = "summoner_indicators")
public class SummonerIndicator {
    @Id
    private Long summonerId;

    private MatchIndicator matchIndicator;
    private LeagueIndicator leagueIndicator;
    private UserIndicator userIndicator;

    public SummonerIndicator(MatchIndicator matchIndicator,
                             LeagueIndicator leagueIndicator,
                             UserIndicator userIndicator) {
        this.matchIndicator = matchIndicator;
        this.leagueIndicator = leagueIndicator;
        this.userIndicator = userIndicator;
    }
}
