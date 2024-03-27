package com.ssafy.matchup_statistics.indicator.service.builder.lane;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.ssafy.matchup_statistics.global.api.rest.RiotRestApiAdaptor;
import com.ssafy.matchup_statistics.global.config.TestConfiguration;
import com.ssafy.matchup_statistics.indicator.entity.Indicator;
import com.ssafy.matchup_statistics.indicator.entity.match.LaneInfo;
import com.ssafy.matchup_statistics.indicator.entity.match.MatchIndicator;
import com.ssafy.matchup_statistics.indicator.entity.match.TeamPosition;
import com.ssafy.matchup_statistics.indicator.service.builder.IndicatorBuilder;
import com.ssafy.matchup_statistics.global.dto.response.MatchDetailResponseDto;
import com.ssafy.matchup_statistics.global.dto.response.MatchTimelineResponseDto;
import com.ssafy.matchup_statistics.match.service.sub.MatchSaveService;
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
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tag("MatchLaneIndicatorBuilderTest")
@Tag("IndicatorTotalTest")
class MatchMidIndicatorBuilderTest {

    @Mock
    RiotRestApiAdaptor riotRestApiAdaptor;

    @Mock
    MatchSaveService matchSaveService;

    @InjectMocks
    IndicatorBuilder target;

    @Autowired
    @Qualifier("hide_on_bush_detail")
    MatchDetailResponseDto matchDetailResponseDto;

    @Autowired
    @Qualifier("hide_on_bush_timeline")
    MatchTimelineResponseDto matchTimelineResponseDto;

    LaneInfo laneInfo;
    MatchIndicator.Metadata metadata;
    String puuid;
    String summonerId;


    @BeforeAll
    public static void initLog() {
        Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.setLevel(Level.INFO);
    }

    @BeforeEach
    void init() {
        target = new IndicatorBuilder(riotRestApiAdaptor, matchSaveService);

        // 본인 아이디 : 3
        // 상대 아이디 : 8
        // 라인 : 미드
        List<String> matches = new ArrayList<>();
        matches.add("KR_6987867218");

        puuid = "c7HMGSOvGZw45MaOwRyrlSVm3Jlrw22YNzm5a8-cmV05vRWQq96caVy3qtKajl5Z8SpJov2PUUkoIw";
        summonerId = "oS78m3OSr-oIcLFL9_9F_Yvy9Jx3GURUnH2-cWOFEKgWHA";
        given(riotRestApiAdaptor.getMatchIdsByPuuid(puuid)).willReturn(matches);
        given(riotRestApiAdaptor.getMatchDetailResponseDtoByMatchId("KR_6987867218")).willReturn(matchDetailResponseDto);
        given(riotRestApiAdaptor.getMatchTimelineResponseDtoByMatchId("KR_6987867218")).willReturn(matchTimelineResponseDto);

        // 라인 정보 빌드
        laneInfo = LaneInfo.builder()
                .teamPosition(TeamPosition.MIDDLE)
                .isBottomLane(false)
                .myLaneNumber(3)
                .myTeamId(100)
                .oppositeLaneNumber(8)
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
    @DisplayName("미드 기초체력 : 경험치, cs 차이 확인")
    void xpCsDifferTest() {
        // given

        // when
        Indicator indicator = target.build(
                riotRestApiAdaptor.getMatchIdsByPuuid(puuid),
                summonerId, puuid
        );

        // then
        assertThat(indicator.getMatchIndicators().get(0)
                .getLaneIndicator()
                .getBasicWeight()
                .getExpDiffer())
                .isEqualTo(7913 - 6895);
        assertThat(indicator.getMatchIndicators().get(0)
                .getLaneIndicator()
                .getBasicWeight()
                .getCsDiffer())
                .isEqualTo(121 - 127);
    }

    @Test
    @Order(3)
    @DisplayName("미드 기초체력 :  포골차이 확인")
    void towerGoldDifferTest() {
        // given

        // when
        Indicator indicator = target.build(
                riotRestApiAdaptor.getMatchIdsByPuuid(puuid),
                summonerId, puuid
        );

        // then
        assertThat(indicator.getMatchIndicators().get(0)
                .getLaneIndicator()
                .getBasicWeight()
                .getTurretPlateDestroyDiffer())
                .isEqualTo(1 - 2);
    }

    @Test
    @Order(4)
    @DisplayName("미드 공격적인 라인전 : 솔킬차이 확인")
    void solokillDifferTest() {
        // given

        // when
        Indicator indicator = target.build(
                riotRestApiAdaptor.getMatchIdsByPuuid(puuid),
                summonerId, puuid
        );

        // then
        assertThat(indicator.getMatchIndicators().get(0)
                .getLaneIndicator()
                .getAggresiveLaneAbilility()
                .getSoloKillDiffer())
                .isEqualTo(2 - 0);
    }


    @Test
    @Order(5)
    @DisplayName("미드 공격적인 라인전 : 딜량차이 확인")
    void dealDifferTest() {
        // given

        // when
        Indicator indicator = target.build(
                riotRestApiAdaptor.getMatchIdsByPuuid(puuid),
                summonerId, puuid
        );

        // then
        assertThat(indicator.getMatchIndicators().get(0)
                .getLaneIndicator()
                .getAggresiveLaneAbilility()
                .getDealDiffer())
                .isEqualTo(9578 - 7038);
    }
}