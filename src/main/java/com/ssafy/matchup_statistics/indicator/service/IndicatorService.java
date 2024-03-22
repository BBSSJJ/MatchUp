package com.ssafy.matchup_statistics.indicator.service;

import com.ssafy.matchup_statistics.account.api.AccountRestApi;
import com.ssafy.matchup_statistics.account.dto.response.AccountResponseDto;
import com.ssafy.matchup_statistics.global.util.MongoTemplateAdaptor;
import com.ssafy.matchup_statistics.indicator.entity.SummonerIndicator;
import com.ssafy.matchup_statistics.indicator.entity.riot.league.LeagueIndicator;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.MatchIndicator;
import com.ssafy.matchup_statistics.indicator.service.builder.LeagueIndicatorBuilder;
import com.ssafy.matchup_statistics.indicator.service.builder.MatchIndicatorBuilder;
import com.ssafy.matchup_statistics.global.api.SummonerRestApi;
import com.ssafy.matchup_statistics.global.dto.response.SummonerInfoResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class IndicatorService {

    private final AccountRestApi accountRestApi;
    private final SummonerRestApi summonerRestApi;
    private final LeagueIndicatorBuilder leagueIndicatorBuilder;
    private final MatchIndicatorBuilder matchIndicatorBuilder;
    private final MongoTemplateAdaptor mongoTemplateAdaptor;

    public SummonerIndicator getSummonerIndicatorBySummonerName(String summonerName) {
        SummonerInfoResponseDto summonerInfo = summonerRestApi.getSummonerInfoResponseDtoBySummonerName(summonerName);
        LeagueIndicator leagueIndicator = leagueIndicatorBuilder.build(summonerInfo.getId());
        List<MatchIndicator> matchIndicators = matchIndicatorBuilder.buildMatches(summonerInfo.getPuuid());

        return new SummonerIndicator(summonerInfo.getId(), matchIndicators, leagueIndicator);
    }

    public SummonerIndicator getSummonerIndicator(String gameName, String tagLine) {
        AccountResponseDto accountDto = accountRestApi.getAccountResponseDto(gameName, tagLine);
        SummonerInfoResponseDto summonerInfo = summonerRestApi.getLeagueInfoResponseDtoByPuuid(accountDto.getPuuid());

        LeagueIndicator leagueIndicator = leagueIndicatorBuilder.build(summonerInfo.getId());
        List<MatchIndicator> matchIndicators = matchIndicatorBuilder.buildMatches(summonerInfo.getPuuid());

        SummonerIndicator summonerIndicator = new SummonerIndicator(summonerInfo.getId(), matchIndicators, leagueIndicator);
        mongoTemplateAdaptor.saveSummonerIndicator(summonerIndicator);
        log.info("indicator : {}", summonerIndicator);

        SummonerIndicator found = mongoTemplateAdaptor.getSummonerIndicatorById(summonerInfo.getId());
        log.info("saved : {}", found);

        return summonerIndicator;
    }

    public SummonerIndicator getSummonerIndicator(String puuid) {
        SummonerInfoResponseDto summonerInfo = summonerRestApi.getLeagueInfoResponseDtoByPuuid(puuid);
        LeagueIndicator leagueIndicator = leagueIndicatorBuilder.build(summonerInfo.getId());
        List<MatchIndicator> matchIndicators = matchIndicatorBuilder.buildMatches(summonerInfo.getPuuid());

        return new SummonerIndicator(summonerInfo.getId(), matchIndicators, leagueIndicator);
    }
}
