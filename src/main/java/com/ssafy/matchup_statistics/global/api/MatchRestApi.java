package com.ssafy.matchup_statistics.global.api;

import com.ssafy.matchup_statistics.global.dto.response.MatchDetailResponseDto;
import com.ssafy.matchup_statistics.global.dto.response.MatchTimelineResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchRestApi {

    private final RestTemplate restTemplate;

    public List<String> getMatchesResponseDtoByPuuid(String puuid) {
        String url = "https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/"
                + puuid + "/ids?start=0&count=20";
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
