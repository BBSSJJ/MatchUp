package com.ssafy.matchup_statistics.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.matchup_statistics.match.api.dto.response.MatchDetailResponseDto;
import com.ssafy.matchup_statistics.match.api.dto.response.MatchTimelineResponseDto;
import org.springframework.context.annotation.Bean;
import java.io.File;
import java.io.IOException;

@org.springframework.boot.test.context.TestConfiguration
public class TestConfiguration {

    ObjectMapper objectMapper = new ObjectMapper();

    @Bean(name = "hide_on_bush_timeline")
    public MatchTimelineResponseDto matchTimelineResponseDto() {
        try {
            return objectMapper.readValue(new File("src/test/resources/object/matchTimelineResponseDto.json"), MatchTimelineResponseDto.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Bean(name = "hide_on_bush_detail")
    public MatchDetailResponseDto matchDetailResponseDto() {
        try {
            return objectMapper.readValue(new File("src/test/resources/object/matchDetailResponseDto.json"), MatchDetailResponseDto.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Bean(name = "kang_chan_bob_timeline")
    public MatchTimelineResponseDto matchKangTimelineResponseDto() {
        try {
            return objectMapper.readValue(new File("src/test/resources/object/강찬밥/강찬밥_240320_1518_timeline.json"), MatchTimelineResponseDto.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Bean(name = "kang_chan_bob_detail")
    public MatchDetailResponseDto matchKangDetailResponseDto() {
        try {
            return objectMapper.readValue(new File("src/test/resources/object/강찬밥/강찬밥_240320_1518_detail.json"), MatchDetailResponseDto.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
