package com.ssafy.matchup_statistics.indicator.service;

import com.ssafy.matchup_statistics.indicator.dto.response.IndicatorResponseDto;
import com.ssafy.matchup_statistics.indicator.entity.Indicator;
import com.ssafy.matchup_statistics.indicator.service.sub.IndicatorRelayService;
import com.ssafy.matchup_statistics.indicator.service.sub.IndicatorSaveService;
import com.ssafy.matchup_statistics.league.dto.request.LeagueEntryRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IndicatorService {

    private final IndicatorRelayService indicatorRelayService;
    private final IndicatorSaveService indicatorSaveService;

    public IndicatorResponseDto getSummonerIndicatorBySummonerName(String summonerName) {
        return indicatorRelayService.getSummonerIndicatorBySummonerName(summonerName);
    }

    public IndicatorResponseDto getSummonerIndicator(String gameName, String tagLine) {
        return indicatorRelayService.getSummonerIndicator(gameName, tagLine);
    }

    public IndicatorResponseDto getSummonerIndicator(String puuid) {
        return indicatorRelayService.getSummonerIndicator(puuid);
    }

    public int buildNewIndicatorsByLeagueEntry(Integer tier, LeagueEntryRequestDto count) {
        return indicatorSaveService.saveLeagueEntryIndicators(tier, count);
    }

    public void buildNewIndicatorByGameName(String gameName, String tagLine) {
        indicatorSaveService.saveSummonerIndicator(gameName, tagLine);
    }
}
