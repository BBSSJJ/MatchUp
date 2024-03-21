package com.ssafy.matchup_statistics.indicator.service.builder;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.ssafy.matchup_statistics.global.config.TestConfiguration;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.LaneInfo;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.MatchIndicator;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.TeamPosition;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.beginning.JgIndicator;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.beginning.LaneIndicator;
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
class MatchJgIndicatorBuilderTest {

    @InjectMocks
    MatchIndicatorBuilder target = new MatchIndicatorBuilder();

    @Qualifier("kang_chan_bob_detail")
    @Autowired
    MatchDetailResponseDto matchDetailResponseDto;

    @Qualifier("kang_chan_bob_timeline")
    @Autowired
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
        // 본인 아이디 : 6
        // 상대 아이디 : 2
        // 라인 : 정글
        List<String> matches = new ArrayList<>();
        matches.add("KR_6994313306");
        puuid = "imNIUu2xgNM-lXg_OtmxXaCS1inoJ0O52sMImruCq-m_UgMhHc0BoJ-neKBd_u0oRwXPV6HusSRc_w";
        given(matchRestApi.getMatchesResponseDtoByPuuid(puuid)).willReturn(matches);
        given(matchRestApi.getMatchDetailResponseDtoByMatchId("KR_6994313306")).willReturn(matchDetailResponseDto);
        given(matchRestApi.getMatchTimelineResponseDtoByMatchId("KR_6994313306")).willReturn(matchTimelineResponseDto);

        // 라인 정보 빌드
        laneInfo = LaneInfo.builder()
                .teamPosition(TeamPosition.JUNGLE)
                .isBottomLane(false)
                .myLaneNumber(7)
                .myTeamId(200)
                .oppositeLaneNumber(2)
                .myBottomDuoNumber(0)
                .oppositeBottomDuoNumber(0)
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
    @DisplayName("정글 기초체력 : 경험치, cs 차이 확인")
    void xpCsDifferTest() {
        // given

        // when
        List<MatchIndicator> matchIndicators = target.buildMatches(puuid);

        // then
        assertThat(matchIndicators.get(0)
                .getLaneIndicator()
                .getBasicWeight()
                .getExpDiffer())
                .isEqualTo(6308 - 5928);
        assertThat(matchIndicators.get(0)
                .getLaneIndicator()
                .getBasicWeight()
                .getCsDiffer())
                .isEqualTo(100 - 98);
    }

    @Test
    @Order(4)
    @DisplayName("정글 라인관여 : 킬어시, 관여율 확인")
    void towerGoldDifferTest() {
        // given

        // when
        List<MatchIndicator> matchIndicators = target.buildMatches(puuid);
        JgIndicator jgIndicator = (JgIndicator) matchIndicators.get(0).getLaneIndicator();
        int killAssistDiffer = jgIndicator.getLaneAssist().getKillAssistDiffer();
        int killInvolvementRate = jgIndicator.getLaneAssist().getKillInvolvementRate();

        // then
        assertThat(killAssistDiffer).isEqualTo(3 + 2 - 2 - 1);
        assertThat(killInvolvementRate).isEqualTo(3 / 8);
    }

    @Test
    @Order(5)
    @DisplayName("정글 공격적인 라인전 : 적진영 킬관여 확인")
    @Tag("MatchIndicatorBuilderTest")
    void solokillDifferTest() {
        // given

        // when
        List<MatchIndicator> matchIndicators = target.buildMatches(puuid);
        JgIndicator jgIndicator = (JgIndicator) matchIndicators.get(0).getLaneIndicator();
        int killInvolvementInEnemyCamp = jgIndicator.getAggresiveJgAbility().getKillInvolvementInEnemyCamp();

        // then
        assertThat(killInvolvementInEnemyCamp).isEqualTo(3 - 1);
    }
}