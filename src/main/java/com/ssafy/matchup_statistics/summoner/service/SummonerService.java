package com.ssafy.matchup_statistics.summoner.service;

import com.ssafy.matchup_statistics.indicator.entity.SummonerIndicator;
import com.ssafy.matchup_statistics.indicator.service.IndicatorService;
import com.ssafy.matchup_statistics.summoner.dto.response.SummonerLeagueInfoResponseDto;
import com.ssafy.matchup_statistics.summoner.service.sub.SummonerLeagueInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SummonerService {

    private final SummonerLeagueInfoService summonerLeagueInfoService;

    public SummonerLeagueInfoResponseDto getSummonerLeagueInfo(String gameName, String tagLine) {
        return summonerLeagueInfoService.createNewSummonerInfoWithLeagueInfo(gameName, tagLine);
    }

    public SummonerLeagueInfoResponseDto getSummonerLeagueInfo(String puuid) {
        return summonerLeagueInfoService.createNewSummonerInfoWithLeagueInfo(puuid);
    }

    public SummonerLeagueInfoResponseDto getSummonerLeagueInfoBySummonerName(String summonerName) {
        return summonerLeagueInfoService.createNewSummonerInfoWithLeagueInfoBySummonerName(summonerName);
    }
}
