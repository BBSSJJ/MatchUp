package com.ssafy.matchup_statistics.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.matchup_statistics.match.api.dto.response.MatchDetailResponseDto;
import com.ssafy.matchup_statistics.match.api.dto.response.MatchTimelineResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.IOException;

@org.springframework.boot.test.context.TestConfiguration
@Slf4j
public class TestConfiguration {

    ObjectMapper objectMapper = new ObjectMapper();
    @Bean
    public MatchTimelineResponseDto matchTimelineResponseDto() {
        try {
            return objectMapper.readValue(new File("src/test/resources/object/matchTimelineResponseDto.json"), MatchTimelineResponseDto.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Bean
    public MatchDetailResponseDto matchDetailResponseDto() {
        try {
            return objectMapper.readValue(new File("src/test/resources/object/matchDetailResponseDto.json"), MatchDetailResponseDto.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
