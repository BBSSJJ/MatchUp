package com.ssafy.matchup_statistics.indicator.service.sub;

import com.ssafy.matchup_statistics.account.api.AccountRestApi;
import com.ssafy.matchup_statistics.account.dto.response.AccountResponseDto;
import com.ssafy.matchup_statistics.global.api.SummonerRestApi;
import com.ssafy.matchup_statistics.global.dto.response.SummonerInfoResponseDto;
import com.ssafy.matchup_statistics.global.util.MongoTemplateAdaptor;
import com.ssafy.matchup_statistics.indicator.entity.Indicator;
import com.ssafy.matchup_statistics.indicator.entity.match.MatchIndicator;
import com.ssafy.matchup_statistics.indicator.service.builder.MatchIndicatorBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class IndicatorRelayService {
    private final AccountRestApi accountRestApi;
    private final SummonerRestApi summonerRestApi;
    private final MatchIndicatorBuilder matchIndicatorBuilder;
    private final MongoTemplateAdaptor mongoTemplateAdaptor;

    public Indicator getSummonerIndicatorBySummonerName(String summonerName) {
        SummonerInfoResponseDto summonerInfo = summonerRestApi.getSummonerInfoResponseDtoBySummonerName(summonerName);
        List<MatchIndicator> matchIndicators = matchIndicatorBuilder.buildMatches(summonerInfo.getPuuid());

        return new Indicator(summonerInfo.getId(), matchIndicators);
    }

    public Indicator getSummonerIndicator(String gameName, String tagLine) {
        AccountResponseDto accountDto = accountRestApi.getAccountResponseDto(gameName, tagLine);
        SummonerInfoResponseDto summonerInfo = summonerRestApi.getLeagueInfoResponseDtoByPuuid(accountDto.getPuuid());

        List<MatchIndicator> matchIndicators = matchIndicatorBuilder.buildMatches(summonerInfo.getPuuid());

        Indicator indicator = new Indicator(summonerInfo.getId(), matchIndicators);
        mongoTemplateAdaptor.saveSummonerIndicator(indicator);

        Indicator found = mongoTemplateAdaptor.getSummonerIndicatorById(summonerInfo.getId());

        return indicator;
    }

    public Indicator getSummonerIndicator(String puuid) {
        SummonerInfoResponseDto summonerInfo = summonerRestApi.getLeagueInfoResponseDtoByPuuid(puuid);
        List<MatchIndicator> matchIndicators = matchIndicatorBuilder.buildMatches(summonerInfo.getPuuid());

        return new Indicator(summonerInfo.getId(), matchIndicators);
    }
}
