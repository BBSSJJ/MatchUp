package com.ssafy.matchup_statistics.indicator.service;

import com.ssafy.matchup_statistics.indicator.entity.Indicator;
import com.ssafy.matchup_statistics.indicator.service.sub.IndicatorRelayService;
import com.ssafy.matchup_statistics.indicator.service.sub.IndicatorSaveService;
import com.ssafy.matchup_statistics.league.dto.request.LeagueEntryRequestDto;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IndicatorService {

    private final IndicatorRelayService indicatorRelayService;
    private final IndicatorSaveService indicatorSaveService;

    public Indicator getSummonerIndicatorBySummonerName(String summonerName) {
        return indicatorRelayService.getSummonerIndicatorBySummonerName(summonerName);
    }

    public Indicator getSummonerIndicator(String gameName, String tagLine) {
        return indicatorRelayService.getSummonerIndicator(gameName, tagLine);
    }

    public Indicator getSummonerIndicator(String puuid) {
        return indicatorRelayService.getSummonerIndicator(puuid);
    }
    public int buildNewIndicators(Integer tier, LeagueEntryRequestDto count) {
        return indicatorSaveService.saveIndicators(tier, count);
    }
}
