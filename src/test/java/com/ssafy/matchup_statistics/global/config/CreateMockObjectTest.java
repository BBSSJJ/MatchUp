package com.ssafy.matchup_statistics.global.config;

import com.ssafy.matchup_statistics.global.dto.response.MatchDetailResponseDto;
import com.ssafy.matchup_statistics.global.dto.response.MatchTimelineResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = TestConfiguration.class)
@ExtendWith(SpringExtension.class)
public class CreateMockObjectTest {

    @Autowired
    MatchTimelineResponseDto matchTimelineResponseDto;

    @Autowired
    MatchDetailResponseDto matchDetailResponseDto;

    @Test
    @DisplayName("타임라인 객체 테스트")
    void mockTimelineObjectTest() {
        assertThat(matchTimelineResponseDto
                .getInfo()
                .getFrames()
                .get(0)
                .getParticipantFrames()._1.currentGold).isEqualTo(500);
    }

    @Test
    @DisplayName("매치Dto 객체 테스트")
    void CreateMatchDtoObjectTest() {
        assertThat(matchDetailResponseDto
                .getInfo()
                .getGameDuration()).isEqualTo(1938L);
    }
}
