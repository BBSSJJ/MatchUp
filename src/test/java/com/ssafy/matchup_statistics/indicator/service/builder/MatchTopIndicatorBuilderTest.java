package com.ssafy.matchup_statistics.indicator.service.builder;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.ssafy.matchup_statistics.global.config.TestConfiguration;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.LaneInfo;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.MatchIndicator;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.TeamPosition;
import com.ssafy.matchup_statistics.match.api.MatchRestApi;
import com.ssafy.matchup_statistics.match.api.dto.response.MatchDetailResponseDto;
import com.ssafy.matchup_statistics.match.api.dto.response.MatchTimelineResponseDto;
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
@Tag("MatchLaneIndicatorBuilderTest")
class MatchTopIndicatorBuilderTest {

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

    @BeforeAll
    public static void initLog() {
        Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.setLevel(Level.ERROR);
    }

    @BeforeEach
    void init() {
        // 본인 아이디 : 5
        // 상대 아이디 : 6
        // 라인 : 탑
        List<String> matches = new ArrayList<>();
        matches.add("KR_6987867218");
        String puuid = "6g8GCdegfX_qreF_OIQCcqOrMPJPiTChca4yeGzxU8FzXHy2M93tNKAC8yq5QziDwwvFiTyUg8NHmg";
        given(matchRestApi.getMatchesResponseDtoByPuuid(puuid)).willReturn(matches);
        given(matchRestApi.getMatchDetailResponseDtoByMatchId("KR_6987867218")).willReturn(matchDetailResponseDto);
        given(matchRestApi.getMatchTimelineResponseDtoByMatchId("KR_6987867218")).willReturn(matchTimelineResponseDto);

        // 라인 정보 빌드
        laneInfo = LaneInfo.builder()
                .teamPosition(TeamPosition.TOP)
                .isBottomLane(false)
                .myLaneNumber(5)
                .myTeamId(100)
                .oppositeLaneNumber(6)
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
        String puuid = "6g8GCdegfX_qreF_OIQCcqOrMPJPiTChca4yeGzxU8FzXHy2M93tNKAC8yq5QziDwwvFiTyUg8NHmg";

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
    @DisplayName("탑 기초체력 : 경험치, cs 차이 확인")
    void xpCsDifferTest() {
        // given
        String puuid = "6g8GCdegfX_qreF_OIQCcqOrMPJPiTChca4yeGzxU8FzXHy2M93tNKAC8yq5QziDwwvFiTyUg8NHmg";

        // when
        List<MatchIndicator> matchIndicators = target.buildMatches(puuid);

        // then
        assertThat(matchIndicators.get(0)
                .getLaneIndicator()
                .getBasicWeight()
                .getExpDiffer())
                .isEqualTo(6583 - 7337);
        assertThat(matchIndicators.get(0)
                .getLaneIndicator()
                .getBasicWeight()
                .getCsDiffer())
                .isEqualTo(105 - 109);
    }

    @Test
    @Order(3)
    @DisplayName("탑 기초체력 :  포골차이 확인")
    void towerGoldDifferTest() {
        // given
        String puuid = "6g8GCdegfX_qreF_OIQCcqOrMPJPiTChca4yeGzxU8FzXHy2M93tNKAC8yq5QziDwwvFiTyUg8NHmg";

        // when
        List<MatchIndicator> matchIndicators = target.buildMatches(puuid);

        // then
        assertThat(matchIndicators.get(0)
                .getLaneIndicator()
                .getBasicWeight()
                .getTowerGoldDiffer())
                .isEqualTo(1 - 2);
    }

    @Test
    @Order(4)
    @DisplayName("탑 공격적인 라인전 : 솔킬차이 확인")
    void solokillDifferTest() {
        // given
        String puuid = "6g8GCdegfX_qreF_OIQCcqOrMPJPiTChca4yeGzxU8FzXHy2M93tNKAC8yq5QziDwwvFiTyUg8NHmg";

        // when
        List<MatchIndicator> matchIndicators = target.buildMatches(puuid);

        // then
        assertThat(matchIndicators.get(0)
                .getLaneIndicator()
                .getAggresiveLaneAbilility()
                .getSoloKillDiffer())
                .isEqualTo(1 - 1);
    }


    @Test
    @Order(5)
    @DisplayName("탑 공격적인 라인전 : 딜량차이 확인")
    void dealDifferTest() {
        // given
        String puuid = "6g8GCdegfX_qreF_OIQCcqOrMPJPiTChca4yeGzxU8FzXHy2M93tNKAC8yq5QziDwwvFiTyUg8NHmg";

        // when
        List<MatchIndicator> matchIndicators = target.buildMatches(puuid);

        // then
        assertThat(matchIndicators.get(0)
                .getLaneIndicator()
                .getAggresiveLaneAbilility()
                .getDealDiffer())
                .isEqualTo(15559 - 10355);
    }
}