package com.ssafy.matchup.user.main.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.matchup.user.main.api.dto.response.SummonerLeagueInfoResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class StatisticsServerApi {
    @Value("${ip.server.statistics}")
    String statisticsServer;

    private final RestTemplate restTemplate;

    public SummonerLeagueInfoResponseDto getRiotAccountInfo(String name, String tag) {
        String url = "http://" + statisticsServer
                + ":9004/api/statistics/summoners/leagues/riot-ids/"
                + name + "/tag-lines/" + tag;
        ObjectMapper objectMapper = new ObjectMapper();
        String response = restTemplate.getForObject(url, String.class);

        try {
            return objectMapper.readValue(response, SummonerLeagueInfoResponseDto.class);
        } catch (Exception e) {
            System.err.println();
            return null;
        }

    }
}

