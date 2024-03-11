package com.ssafy.matchup_statistics.summoner.api;

import com.ssafy.matchup_statistics.summoner.api.dto.response.LeagueInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
@RequiredArgsConstructor
public class LeagueRestApi {

    private final RestTemplate restTemplate;

    public LeagueInfoResponseDto getLeagueResponseDtoById(String id) {
        String url = "https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/" + id;
        return restTemplate.getForObject(url, LeagueInfoResponseDto.class);
    }
}
