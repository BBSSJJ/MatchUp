package com.ssafy.matchup_statistics.indicator.service.builder;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.ssafy.matchup_statistics.global.api.rest.RiotRestApiAdaptor;
import com.ssafy.matchup_statistics.global.config.TestConfiguration;
import com.ssafy.matchup_statistics.global.dto.response.MatchDetailResponseDto;
import com.ssafy.matchup_statistics.global.dto.response.MatchTimelineResponseDto;
import com.ssafy.matchup_statistics.indicator.entity.Indicator;
import com.ssafy.matchup_statistics.indicator.entity.match.LaneInfo;
import com.ssafy.matchup_statistics.indicator.entity.match.MatchIndicator;
import com.ssafy.matchup_statistics.indicator.entity.match.TeamPosition;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ContextConfiguration(classes = TestConfiguration.class)
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@EnableConfigurationProperties
@Slf4j
@Tag("MatchMacroIndicatorBuilderTest")
@Tag("IndicatorTotalTest")
class MatchMacroIndicatorBuilderTest {

    private final int DEFAULT_ROUND_UP = 100_000;
    @Mock
    RiotRestApiAdaptor riotRestApiAdaptor;
    @Mock
    MongoTemplate mongoTemplate;
    @InjectMocks
    IndicatorBuilder target;
    @Qualifier("kang_chan_bob_detail")
    @Autowired
    MatchDetailResponseDto matchDetailResponseDto;

    @Qualifier("kang_chan_bob_timeline")
    @Autowired
    MatchTimelineResponseDto matchTimelineResponseDto;

    LaneInfo laneInfo;
    MatchIndicator.Metadata metadata;
    String puuid;
    String summonerId;

    @BeforeAll
    public static void initLog() {
        Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.setLevel(Level.ERROR);
    }

    @BeforeEach
    void init() {
        target = new IndicatorBuilder(riotRestApiAdaptor, mongoTemplate);

        // 본인 아이디 : 6
        // 상대 아이디 : 2
        // 라인 : 정글
        List<String> matches = new ArrayList<>();
        matches.add("KR_6994313306");

        puuid = "GweS1V-eVKk-je-x4D6znocszRr02LsMmfeOOykWurawl050dAbp3S8NcGV1JinmjysCLkS5_VOrYQ";
        summonerId = "XpfTc8FZVplKFNyQJyIXDbHwspU2I0qL2yjau8S7y5qk2w";
        given(riotRestApiAdaptor.getMatchIdsByPuuid(puuid)).willReturn(matches);
        given(riotRestApiAdaptor.getMatchDetailResponseDtoByMatchId("KR_6994313306")).willReturn(matchDetailResponseDto);
        given(riotRestApiAdaptor.getMatchTimelineResponseDtoByMatchId("KR_6994313306")).willReturn(matchTimelineResponseDto);

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
        Indicator indicator = target.build(
                riotRestApiAdaptor.getMatchIdsByPuuid(puuid),
                summonerId, puuid
        );

        // then
        assertThat(indicator.getMatchIndicators().get(0)
                .getMetadata()
                .getLaneInfo())
                .usingRecursiveComparison()
                .isEqualTo(laneInfo);
    }

    @Test
    @Order(2)
    @DisplayName("스플릿 점수 확인 : 타워철거, 타워 데미지 비중, 팀 내 타워데미지 비중")
    void splitPointTest() {
        // given

        // when
        Indicator indicator = target.build(
                riotRestApiAdaptor.getMatchIdsByPuuid(puuid),
                summonerId, puuid
        );

        // then
        // 타워철거
        assertThat(indicator.getMatchIndicators().get(0)
                .getMacroIndicator()
                .getSplitPoint()
                .getTurretKillsPerDeaths())
                .isEqualTo(1 * DEFAULT_ROUND_UP / (2 + 1));
        // 타워 데미지 비중
        assertThat(indicator.getMatchIndicators().get(0)
                .getMacroIndicator()
                .getSplitPoint()
                .getDamageDealtToTurretsPerTotalDamageDealt())
                .isEqualTo(3734 * DEFAULT_ROUND_UP / (327344 + 1));
        // 팀 내 타워데미지 비중
        assertThat(indicator.getMatchIndicators().get(0)
                .getMacroIndicator()
                .getSplitPoint()
                .getDamageDealtToTurretsPerTeamTotalTowerDamageDone())
                .isEqualTo(3734 * DEFAULT_ROUND_UP / (6714 + 3734 + 3592 + 6015 + 920 + 1));
    }

    @Test
    @Order(3)
    @DisplayName("이니시 점수 확인 : cc시간, 받은 피해량, 감소시킨 데미지")
    void initiatingPointTest() {
        // given

        // when
        Indicator indicator = target.build(
                riotRestApiAdaptor.getMatchIdsByPuuid(puuid),
                summonerId, puuid
        );

        // then
        // cc시간
        assertThat(indicator.getMatchIndicators().get(0)
                .getMacroIndicator()
                .getInitiatingPoint()
                .getTotalTimeCCingOthersPerTotalDamageTaken())
                .isEqualTo(13 * DEFAULT_ROUND_UP / (27649 + 1));

        // 받은 피해량
        assertThat(indicator.getMatchIndicators().get(0)
                .getMacroIndicator()
                .getInitiatingPoint()
                .getTotalDamageTakenPerTeamTotalDamageTaken())
                .isEqualTo((long) 27649 * DEFAULT_ROUND_UP / (17452 + 27649 + 18517 + 13622 + 18728 + 1));

        // 감소시킨 데미지
        assertThat(indicator.getMatchIndicators().get(0)
                .getMacroIndicator()
                .getInitiatingPoint()
                .getDamageSelfMitigatedPerTotalDamageTaken())
                .isEqualTo((long) 15806 * DEFAULT_ROUND_UP / (27649 + 1));
    }


    @Test
    @Order(4)
    @DisplayName("정글 장악 점수 : 빼먹은 정글몬스터")
    void getJungleHoldPointTest() {
        // given

        // when
        Indicator indicator = target.build(
                riotRestApiAdaptor.getMatchIdsByPuuid(puuid),
                summonerId, puuid
        );

        // then
        // 빼먹은 정글몬스터
        assertThat(indicator.getMatchIndicators().get(0)
                .getMacroIndicator()
                .getJungleHoldPoint()
                .getTotalJungleObjectivePerGameDuration())
                .isEqualTo(27 * DEFAULT_ROUND_UP / (1713 + 1));
    }

    @Test
    @Order(5)
    @DisplayName("오브젝트 점수 : 획득한 오브젝트 차이")
    void getObjectivePointTest() {
        // given

        // when
        Indicator indicator = target.build(
                riotRestApiAdaptor.getMatchIdsByPuuid(puuid),
                summonerId, puuid
        );

        // then
        // 획득한 오브젝트 차이
        assertThat(indicator.getMatchIndicators().get(0)
                .getMacroIndicator()
                .getObjectivePoint()
                .getGetObjectiveDifferPerGameDuration())
                .isEqualTo(((0 + 3 + 2 + 0) - (1 + 1 + 3 + 1)) * DEFAULT_ROUND_UP / (1713 + 1));
    }

    @Test
    @Order(6)
    @DisplayName("시야 점수 : 시야 점수")
    void getVisionPointTest() {
        // given

        // when
        Indicator indicator = target.build(
                riotRestApiAdaptor.getMatchIdsByPuuid(puuid),
                summonerId, puuid
        );

        // then
        // 시야 점수(death + 1)
        assertThat(indicator.getMatchIndicators().get(0)
                .getMacroIndicator()
                .getVisionPoint()
                .getVisionScorePerDeath())
                .isEqualTo(41 * DEFAULT_ROUND_UP / (2 + 1));
    }

    @Test
    @Order(6)
    @DisplayName("딜 점수 : 분당 딜, 골드당 딜, 딜 비중")
    void getTotalDealPointTest() {
        // given

        // when
        Indicator indicator = target.build(
                riotRestApiAdaptor.getMatchIdsByPuuid(puuid),
                summonerId, puuid
        );

        // then
        // 분당 딜
        assertThat(indicator.getMatchIndicators().get(0)
                .getMacroIndicator()
                .getTotalDealPoint()
                .getDamagePerMinute())
                .isEqualTo((long) 899.0909564443765 * DEFAULT_ROUND_UP);
        // 골드당 딜
        assertThat(indicator.getMatchIndicators().get(0)
                .getMacroIndicator()
                .getTotalDealPoint()
                .getDealPerGold())
                .isEqualTo((long) ((long) 899.0909564443765 * DEFAULT_ROUND_UP / (496.7942995806239 + 1)));
        // 딜 비중
        assertThat(indicator.getMatchIndicators().get(0)
                .getMacroIndicator()
                .getTotalDealPoint()
                .getTeamDamagePercentage())
                .isEqualTo(0.253161905915865);
    }
}