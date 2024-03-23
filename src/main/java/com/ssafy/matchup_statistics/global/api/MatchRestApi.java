package com.ssafy.matchup_statistics.global.api;

import com.ssafy.matchup_statistics.global.dto.response.MatchDetailResponseDto;
import com.ssafy.matchup_statistics.global.dto.response.MatchTimelineResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MatchRestApi {

    private final RestTemplate restTemplate;

    public List<String> getMatchesResponseDtoByPuuid(String puuid) {
        URI url = UriComponentsBuilder
                .fromUriString("https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/")
                .path(puuid + "/ids")
                .queryParam("start", 0)
                .queryParam("count", 20)
                .queryParam("type", "ranked")
                .encode()
                .build()
                .toUri();
        return restTemplate.getForObject(url, List.class);
    }

    public MatchDetailResponseDto getMatchDetailResponseDtoByMatchId(String matchId) {
        String url = "https://asia.api.riotgames.com/lol/match/v5/matches/" + matchId;
        return restTemplate.getForObject(url, MatchDetailResponseDto.class);
    }

    public MatchTimelineResponseDto getMatchTimelineResponseDtoByMatchId(String matchId) {
        String url = "https://asia.api.riotgames.com/lol/match/v5/matches/" + matchId + "/timeline";
        return restTemplate.getForObject(url, MatchTimelineResponseDto.class);
    }
}
