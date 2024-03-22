package com.ssafy.matchup_statistics.summoner.api;

import com.ssafy.matchup_statistics.league.dto.response.LeagueInfoResponseDto;
import com.ssafy.matchup_statistics.summoner.dto.response.SummonerInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SummonerRestApi {

    private final RestTemplate restTemplate;

    public SummonerInfoResponseDto getSummonerInfoResponseDtoBySummonerName(String summonerName) {
        String url = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + summonerName;
        return restTemplate.getForObject(url, SummonerInfoResponseDto.class);
    }

    public SummonerInfoResponseDto getLeagueInfoResponseDtoByPuuid(String puuid) {
        String url = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-puuid/" + puuid;
        return restTemplate.getForObject(url, SummonerInfoResponseDto.class);
    }
}
