package com.ssafy.matchup_statistics.summoner.api;

import com.ssafy.matchup_statistics.summoner.api.dto.response.LeagueInfoResponseDto;
import com.ssafy.matchup_statistics.summoner.api.dto.response.SummonerInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class SummonerRestApi {

    private final RestTemplate restTemplate;

    public SummonerInfoResponseDto getSummonerInfoResponseDtoByName(String id) {
        String url = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + id;
        return restTemplate.getForObject(url, SummonerInfoResponseDto.class);
    }
}
