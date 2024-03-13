package com.ssafy.matchup_statistics.league.api;

import com.ssafy.matchup_statistics.league.api.dto.response.LeagueInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
@Component
@RequiredArgsConstructor
public class LeagueRestApi {

    private final RestTemplate restTemplate;

    public LeagueInfoResponseDto getLeagueResponseDtoById(String id) {
        String url = "https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/" + id;
        return restTemplate.getForObject(url, LeagueInfoResponseDto.class);
    }
}
