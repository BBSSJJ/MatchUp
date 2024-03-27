package com.ssafy.matchup_statistics.global.api.rest;

import com.ssafy.matchup_statistics.global.dto.response.SummonerInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class SummonerRestApi {

    private final RestTemplate restTemplate;

    public SummonerInfoResponseDto getSummonerInfoResponseDtoBySummonerName(String summonerName) {
        String url = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + summonerName;
        return restTemplate.getForObject(url, SummonerInfoResponseDto.class);
    }

    public SummonerInfoResponseDto getSummonerInfoResponseDtoByPuuid(String puuid) {
        String url = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-puuid/" + puuid;
        return restTemplate.getForObject(url, SummonerInfoResponseDto.class);
    }
}
