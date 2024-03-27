package com.ssafy.matchup_statistics.indicator.service.sub;

import com.ssafy.matchup_statistics.global.api.rest.RiotRestApiAdaptor;
import com.ssafy.matchup_statistics.global.dto.response.LeagueInfoResponseDto;
import com.ssafy.matchup_statistics.global.dto.response.SummonerInfoResponseDto;
import com.ssafy.matchup_statistics.indicator.entity.Indicator;
import com.ssafy.matchup_statistics.indicator.service.builder.IndicatorBuilder;
import com.ssafy.matchup_statistics.league.dto.request.LeagueEntryRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
//TODO : 지표 저장하는 로직 추가
public class IndicatorSaveService {

    private final RiotRestApiAdaptor riotRestApiAdaptor;
    private final IndicatorBuilder indicatorBuilder;

    public int saveLeagueEntryIndicators(Integer count, LeagueEntryRequestDto dto) {
        List<LeagueInfoResponseDto> leagueInfoResponseByTier = riotRestApiAdaptor.getLeagueInfoResponseByTier(count, dto);
        return 0;
    }

    public void saveSummonerIndicator(String gameName, String tagLine) {
        SummonerInfoResponseDto summonerInfo = riotRestApiAdaptor.getSummonerInfo(gameName, tagLine);
        List<String> matchIds = riotRestApiAdaptor.getMatchIdsByPuuid(summonerInfo.getPuuid());
        Indicator indicator = indicatorBuilder.build(matchIds, summonerInfo.getId(), summonerInfo.getPuuid());
    }
}
