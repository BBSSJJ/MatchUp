package com.ssafy.matchup_statistics.indicator.service;

import com.ssafy.matchup_statistics.indicator.entity.SummonerIndicator;
import com.ssafy.matchup_statistics.indicator.entity.riot.league.LeagueIndicator;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.MatchIndicator;
import com.ssafy.matchup_statistics.indicator.service.builder.LeagueIndicatorBuilder;
import com.ssafy.matchup_statistics.indicator.service.builder.MatchIndicatorBuilder;
import com.ssafy.matchup_statistics.summoner.api.SummonerRestApi;
import com.ssafy.matchup_statistics.summoner.api.dto.response.SummonerInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SummonerIndicatorService {

    private final SummonerRestApi summonerRestApi;
    private final LeagueIndicatorBuilder leagueIndicatorBuilder;
    private final MatchIndicatorBuilder matchIndicatorBuilder;

    public SummonerIndicator createSummonerIndicatorBySummonerName(String summonerName) {
        SummonerInfoResponseDto summonerInfo = summonerRestApi.getSummonerInfoResponseDtoByName(summonerName);
        LeagueIndicator leagueIndicator = leagueIndicatorBuilder.build(summonerInfo.getId());
        List<MatchIndicator> matchIndicators = matchIndicatorBuilder.buildMatches(summonerInfo.getPuuid());

        SummonerIndicator summonerIndicator = new SummonerIndicator(summonerInfo.getId(), matchIndicators, leagueIndicator);
        summonerIndicator.eraseUnnecessarilyField();

        return summonerIndicator;
    }
}
