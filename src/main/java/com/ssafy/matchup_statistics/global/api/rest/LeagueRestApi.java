package com.ssafy.matchup_statistics.global.api.rest;

import com.ssafy.matchup_statistics.global.dto.response.LeagueInfoResponseDto;
import com.ssafy.matchup_statistics.global.exception.RiotApiError;
import com.ssafy.matchup_statistics.global.exception.RiotApiException;
import com.ssafy.matchup_statistics.league.dto.request.LeagueEntryRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class LeagueRestApi {

    private final RestTemplate restTemplate;

    public LeagueInfoResponseDto getLeagueInfoResponseBySummonerId(String summonerId) {
        String url = "https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/" + summonerId;
        List<LinkedHashMap<String, Object>> response = restTemplate.getForObject(url, List.class);
        if (response.isEmpty()) return new LeagueInfoResponseDto();
        return new LeagueInfoResponseDto(response.get(0));
    }

    public List<LeagueInfoResponseDto> getLeagueInfoResponseByTier(int page, LeagueEntryRequestDto dto) {
        URI url = UriComponentsBuilder
                .fromUriString("https://kr.api.riotgames.com/lol/league/v4/entries")
                .path(dto.getLeagueEntryRequestUrl())
                .queryParam("page", page)
                .encode()
                .build()
                .toUri();
        List<LinkedHashMap<String, Object>> response = restTemplate.getForObject(url, List.class);
        if (response.isEmpty()) throw new RiotApiException(RiotApiError.NO_LEAGUE_ENTRY_ERROR);
        return response.stream().map(r -> new LeagueInfoResponseDto(r)).toList();
    }
}
