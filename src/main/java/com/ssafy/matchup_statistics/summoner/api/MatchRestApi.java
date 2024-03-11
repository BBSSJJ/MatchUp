package com.ssafy.matchup_statistics.summoner.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
@RequiredArgsConstructor
public class MatchRestApi {

    private final RestTemplate restTemplate;

    // TODO : 최근 20개 Match 정보 획득하는 API
//    public LeagueInfoResponseDto getLeagueResponseDtoById(String id) {
//        String url = "https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/" + id;
//        return restTemplate.getForObject(url, LeagueInfoResponseDto.class);
//    }
    // TODO : 각 매치의 상세정보를 획득하는 API
}
