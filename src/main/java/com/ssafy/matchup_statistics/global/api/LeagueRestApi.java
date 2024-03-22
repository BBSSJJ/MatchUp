package com.ssafy.matchup_statistics.global.api;

import com.ssafy.matchup_statistics.league.dto.response.LeagueInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LeagueRestApi {

    private final RestTemplate restTemplate;

    public LeagueInfoResponseDto getLeagueInfoResponseDtoBySummonerId(String summonerId) {
        String url = "https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/" + summonerId;
        List<LinkedHashMap<String, Object>> response = restTemplate.getForObject(url, List.class);
        if (response.isEmpty()) return new LeagueInfoResponseDto();
        return new LeagueInfoResponseDto(response.get(0));
    }
}
