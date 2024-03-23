package com.ssafy.matchup_statistics.indicator.service.sub;

import com.ssafy.matchup_statistics.global.api.RiotApiAdaptor;
import com.ssafy.matchup_statistics.global.dto.response.SummonerInfoResponseDto;
import com.ssafy.matchup_statistics.global.util.mapper.IndicatorMapper;
import com.ssafy.matchup_statistics.indicator.dto.response.IndicatorResponseDto;
import com.ssafy.matchup_statistics.indicator.entity.Indicator;
import com.ssafy.matchup_statistics.indicator.entity.match.MatchIndicator;
import com.ssafy.matchup_statistics.indicator.service.builder.IndicatorBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class IndicatorRelayService {
    private final RiotApiAdaptor riotApiAdaptor;
    private final IndicatorBuilder indicatorBuilder;
    private final IndicatorMapper indicatorMapper;

    public IndicatorResponseDto getSummonerIndicatorBySummonerName(String summonerName) {
        SummonerInfoResponseDto summonerInfo = riotApiAdaptor.getSummonerInfoBySummonerName(summonerName);
        List<String> matchIds = riotApiAdaptor.getMatchIdsByPuuid(summonerInfo.getPuuid());
        Indicator indicator = indicatorBuilder.build(matchIds, summonerInfo.getId(), summonerInfo.getPuuid());
        return indicatorMapper.indicatorToIndicatorResponseDto(indicator);
    }

    public IndicatorResponseDto getSummonerIndicator(String gameName, String tagLine) {
        SummonerInfoResponseDto summonerInfo = riotApiAdaptor.getSummonerInfo(gameName, tagLine);
        List<String> matchIds = riotApiAdaptor.getMatchIdsByPuuid(summonerInfo.getPuuid());
        Indicator indicator = indicatorBuilder.build(matchIds, summonerInfo.getId(), summonerInfo.getPuuid());
        return indicatorMapper.indicatorToIndicatorResponseDto(indicator);
    }

    public IndicatorResponseDto getSummonerIndicator(String puuid) {
        SummonerInfoResponseDto summonerInfo = riotApiAdaptor.getSummonerInfo(puuid);
        List<String> matchIds = riotApiAdaptor.getMatchIdsByPuuid(summonerInfo.getPuuid());
        Indicator indicator = indicatorBuilder.build(matchIds, summonerInfo.getId(), summonerInfo.getPuuid());
        return indicatorMapper.indicatorToIndicatorResponseDto(indicator);
    }
}
