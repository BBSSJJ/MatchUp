package com.ssafy.matchup_statistics.league.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LeagueRestApi {

    private final RestTemplate restTemplate;

    public LinkedHashMap<String, Object> getLeagueResponseDtoById(String id) {
        String url = "https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/" + id;
        List<LinkedHashMap<String, Object>> response = restTemplate.getForObject(url, List.class);
        return response.get(0);
    }
}
