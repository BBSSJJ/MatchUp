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
@Tag("MatchMacroIndicatorBuilderTest")
class MatchMacroIndicatorBuilderTest {
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
    @DisplayName("스플릿 점수 확인 : 타워철거, 타워 데미지 비중, 팀 내 타워데미지 비중")
    void splitPointTest() {
        // given

        // when
        List<MatchIndicator> matchIndicators = target.buildMatches(puuid);

        // then
        // 타워철거
        assertThat(matchIndicators.get(0)
                .getMacroIndicator()
                .getSplitPoint()
                .getDeathsDifferTurretKills())
                .isEqualTo(2 - 1);
        // 타워 데미지 비중
        assertThat(matchIndicators.get(0)
                .getMacroIndicator()
                .getSplitPoint()
                .getDamageDealtToTurretsPerTotalDamageDealt())
                .isEqualTo((double) 3734 / 327344);
        // 팀 내 타워데미지 비중
        assertThat(matchIndicators.get(0)
                .getMacroIndicator()
                .getSplitPoint()
                .getDamageDealtToTurretsPerTeamTotalTowerDamageDone())
                .isEqualTo((double) 3734 / (6714 + 3734 + 3592 + 6015 + 920));
    }

    @Test
    @Order(3)
    @DisplayName("이니시 점수 확인 : cc시간, 받은 피해량, 감소시킨 데미지")
    void initiatingPointTest() {
        // given

        // when
        List<MatchIndicator> matchIndicators = target.buildMatches(puuid);

        // then
        // cc시간
        assertThat(matchIndicators.get(0)
                .getMacroIndicator()
                .getInitiatingPoint()
                .getTotalTimeCCingOthersPerTotalDamageTaken())
                .isEqualTo((double) 28 / 27649);

        // 받은 피해량
        assertThat(matchIndicators.get(0)
                .getMacroIndicator()
                .getInitiatingPoint()
                .getTotalDamageTakenPerTeamTotalDamageTaken())
                .isEqualTo((double) 27649 / (17452 + 27649 + 18517 + 13622 + 18728));

        // 감소시킨 데미지
        assertThat(matchIndicators.get(0)
                .getMacroIndicator()
                .getInitiatingPoint()
                .getDamageSelfMitigatedPerTotalDamageTaken())
                .isEqualTo((double) 15806 / 27649);
    }


    @Test
    @Order(4)
    @DisplayName("정글 장악 점수 : 빼먹은 정글몬스터")
    void getJungleHoldPointTest() {
        // given

        // when
        List<MatchIndicator> matchIndicators = target.buildMatches(puuid);

        // then
        // 빼먹은 정글몬스터
        assertThat(matchIndicators.get(0)
                .getMacroIndicator()
                .getJungleHoldPoint()
                .getTotalJungleObjectivePerGameDuration())
                .isEqualTo((double) 27 / 1713);
    }

    @Test
    @Order(5)
    @DisplayName("오브젝트 점수 : 획득한 오브젝트 차이")
    void getObjectivePointTest() {
        // given

        // when
        List<MatchIndicator> matchIndicators = target.buildMatches(puuid);

        // then
        // 획득한 오브젝트 차이
        assertThat(matchIndicators.get(0)
                .getMacroIndicator()
                .getObjectivePoint()
                .getGetObjectiveDiffer())
                .isEqualTo((0 + 3 + 2 + 0) - (1 + 1 + 3 + 1));
    }

    @Test
    @Order(6)
    @DisplayName("시야 점수 : 시야 점수")
    void getVisionPointTest() {
        // given

        // when
        List<MatchIndicator> matchIndicators = target.buildMatches(puuid);

        // then
        // 시야 점수(death + 1)
        assertThat(matchIndicators.get(0)
                .getMacroIndicator()
                .getVisionPoint()
                .getVisionScorePerDeath())
                .isEqualTo(41 / (2 + 1));
    }

    @Test
    @Order(6)
    @DisplayName("딜 점수 : 분당 딜, 골드당 딜, 딜 비중")
    void getTotalDealPointTest() {
        // given

        // when
        List<MatchIndicator> matchIndicators = target.buildMatches(puuid);

        // then
        // 분당 딜
        assertThat(matchIndicators.get(0)
                .getMacroIndicator()
                .getTotalDealPoint()
                .getDamagePerMinute())
                .isEqualTo(899.0909564443765);
        // 골드당 딜
        assertThat(matchIndicators.get(0)
                .getMacroIndicator()
                .getTotalDealPoint()
                .getDealPerGold())
                .isEqualTo(899.0909564443765 / 345.698109566159);
        // 딜 비중
        assertThat(matchIndicators.get(0)
                .getMacroIndicator()
                .getTotalDealPoint()
                .getTeamDamagePercentage())
                .isEqualTo(0.253161905915865);
    }
}