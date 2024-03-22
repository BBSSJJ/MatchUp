package com.ssafy.matchup_statistics.summoner.service;

import com.ssafy.matchup_statistics.indicator.entity.SummonerIndicator;
import com.ssafy.matchup_statistics.indicator.service.SummonerIndicatorService;
import com.ssafy.matchup_statistics.summoner.dto.response.SummonerLeagueInfoResponseDto;
import com.ssafy.matchup_statistics.summoner.service.sub.SummonerLeagueInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SummonerService {

    private final SummonerIndicatorService summonerIndicatorService;
    private final SummonerLeagueInfoService summonerLeagueInfoService;
    private final MongoTemplate mongoTemplate;

    public SummonerIndicator getNewSummonerIndicator(String name) {
        SummonerIndicator summonerIndicator = summonerIndicatorService.createSummonerIndicatorBySummonerName(name);
        mongoTemplate.save(summonerIndicator);
        return summonerIndicator;
    }

    public SummonerLeagueInfoResponseDto getSummonerDefaultInfo(String gameName, String tagLine) {
        return summonerLeagueInfoService.createNewSummonerInfoWithLeagueInfo(gameName, tagLine);
    }

    public SummonerLeagueInfoResponseDto getSummonerDefaultInfo(String puuid) {
        return summonerLeagueInfoService.createNewSummonerInfoWithLeagueInfo(puuid);
    }

    public SummonerLeagueInfoResponseDto getSummonerDefaultInfoBySummonerName(String summonerName) {
        return summonerLeagueInfoService.createNewSummonerInfoWithLeagueInfoBySummonerName(summonerName);
    }
}
