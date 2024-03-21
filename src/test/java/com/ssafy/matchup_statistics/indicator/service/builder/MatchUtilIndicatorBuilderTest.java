package com.ssafy.matchup_statistics.indicator.service.builder;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.ssafy.matchup_statistics.global.config.TestConfiguration;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.LaneInfo;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.MatchIndicator;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.TeamPosition;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.beginning.JgIndicator;
import com.ssafy.matchup_statistics.match.api.MatchRestApi;
import com.ssafy.matchup_statistics.match.api.dto.response.MatchDetailResponseDto;
import com.ssafy.matchup_statistics.match.api.dto.response.MatchTimelineResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ContextConfiguration(classes = TestConfiguration.class)
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
@Tag("MatchIndicatorBuilderTest")
class MatchUtilIndicatorBuilderTest {
    @InjectMocks
    MatchIndicatorBuilder target = new MatchIndicatorBuilder();

    @Autowired
    @Qualifier("hide_on_bush_detail")
    MatchDetailResponseDto matchDetailResponseDto;

    @Autowired
    @Qualifier("hide_on_bush_timeline")
    MatchTimelineResponseDto matchTimelineResponseDto;

    @Mock
    MatchRestApi matchRestApi;

    LaneInfo laneInfo;
    MatchIndicator.Metadata metadata;
    String puuid;

    @BeforeAll
    public static void initLog() {
        Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.setLevel(Level.ERROR);
    }

    @BeforeEach
    void init() {
        // 본인 아이디 : 1
        // 상대 아이디 : 10
        // 라인 : 서폿
        List<String> matches = new ArrayList<>();
        matches.add("KR_6987867218");
        puuid = "MluI4kf3Z0BNzvXCb9fQiMakbrYU2_sOJEwYmpfC3RtlZpyBnN2uHHh9VveJYqK7FSEA5sTfIoCY_g";
        given(matchRestApi.getMatchesResponseDtoByPuuid(puuid)).willReturn(matches);
        given(matchRestApi.getMatchDetailResponseDtoByMatchId("KR_6987867218")).willReturn(matchDetailResponseDto);
        given(matchRestApi.getMatchTimelineResponseDtoByMatchId("KR_6987867218")).willReturn(matchTimelineResponseDto);

        // 라인 정보 빌드
        laneInfo = LaneInfo.builder()
                .teamPosition(TeamPosition.UTILITY)
                .isBottomLane(true)
                .myLaneNumber(1)
                .myTeamId(100)
                .oppositeLaneNumber(10)
                .myBottomDuoNumber(4)
                .oppositeBottomDuoNumber(9)
                .build();

        // 메타정보 빌드
        metadata = MatchIndicator.Metadata.builder()
                .laneInfo(laneInfo)
                .isFinishedBeforeFifteen(false)
                .build();

    }

    @Test
    @Order(1)
    @DisplayName("라인 정보 잘 가져오는지 테스트")
    void laneInfoTest() {
        // given

        // when
        List<MatchIndicator> matchIndicators = target.buildMatches(puuid);

        // then
        assertThat(matchIndicators.get(0)
                .getMetadata()
                .getLaneInfo())
                .isEqualTo(laneInfo);
    }

    @Test
    @Order(2)
    @DisplayName("메타데이터 잘 가져오는지 테스트")
    void metadataTest() {
        // given

        // when
        List<MatchIndicator> matchIndicators = target.buildMatches(puuid);

        // then
        assertThat(matchIndicators.get(0)
                .getMetadata())
                .isEqualTo(metadata);
    }

    @Test
    @Order(3)
    @DisplayName("서폿 기초체력 : 경험치 차이 확인")
    void xpCsDifferTest() {
        // given

        // when
        List<MatchIndicator> matchIndicators = target.buildMatches(puuid);

        // then
        assertThat(matchIndicators.get(0)
                .getLaneIndicator()
                .getBasicWeight()
                .getExpDiffer())
                .isEqualTo(4393 - 3887);
    }

    @Test
    @Order(4)
    @DisplayName("서폿 기초체력 : 서폿템 완성시간 확인")
    void towerGoldDifferTest() {
        // given

        // when
        List<MatchIndicator> matchIndicators = target.buildMatches(puuid);

        // then
        assertThat(matchIndicators.get(0)
                .getLaneIndicator()
                .getBasicWeight()
                .getTowerGoldDiffer())
                // TODO : 확인필요
                .isEqualTo(1 - 2);
    }

    @Test
    @Order(5)
    @DisplayName("원딜 공격적인 라인전 : 솔킬 및 듀오킬 차이 확인")
    void solokillDifferTest() {
        // given

        // when
        List<MatchIndicator> matchIndicators = target.buildMatches(puuid);

        // then
        assertThat(matchIndicators.get(0)
                .getLaneIndicator()
                .getAggresiveLaneAbilility()
                .getSoloKillDiffer())
                // TODO : 확인필요
                .isEqualTo(2 - 0);
    }


    @Test
    @Order(6)
    @DisplayName("서폿 공격적인 라인전 : 딜량차이 확인")
    void dealDifferTest() {
        // given

        // when
        List<MatchIndicator> matchIndicators = target.buildMatches(puuid);

        // then
        assertThat(matchIndicators.get(0)
                .getLaneIndicator()
                .getAggresiveLaneAbilility()
                .getDealDiffer())
                .isEqualTo(1691 - 2762);
    }
}