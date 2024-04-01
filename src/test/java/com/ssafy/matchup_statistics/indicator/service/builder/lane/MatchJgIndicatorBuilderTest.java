package com.ssafy.matchup_statistics.indicator.service.builder.lane;

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
import com.ssafy.matchup_statistics.indicator.entity.match.beginning.JgIndicator;
import com.ssafy.matchup_statistics.indicator.service.builder.IndicatorBuilder;
import com.ssafy.matchup_statistics.match.dao.MatchDaoImpl;
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
@Tag("MatchLaneIndicatorBuilderTest")
@Tag("IndicatorTotalTest")
class MatchJgIndicatorBuilderTest {

    @Mock
    RiotRestApiAdaptor riotApiAdaptor;

    @Mock
    MatchDaoImpl matchDaoImpl;

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
        target = new IndicatorBuilder(riotApiAdaptor, matchDaoImpl);

        // 본인 아이디 : 6
        // 상대 아이디 : 2
        // 라인 : 정글
        List<String> matches = new ArrayList<>();
        matches.add("KR_6994313306");

        puuid = "GweS1V-eVKk-je-x4D6znocszRr02LsMmfeOOykWurawl050dAbp3S8NcGV1JinmjysCLkS5_VOrYQ";
        summonerId = "XpfTc8FZVplKFNyQJyIXDbHwspU2I0qL2yjau8S7y5qk2w";
        given(riotApiAdaptor.getMatchIdsByPuuid(puuid)).willReturn(matches);
        given(riotApiAdaptor.getMatchDetailResponseDtoByMatchId("KR_6994313306")).willReturn(matchDetailResponseDto);
        given(riotApiAdaptor.getMatchTimelineResponseDtoByMatchId("KR_6994313306")).willReturn(matchTimelineResponseDto);

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
                riotApiAdaptor.getMatchIdsByPuuid(puuid),
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
    @DisplayName("정글 기초체력 : 경험치, cs 차이 확인")
    void xpCsDifferTest() {
        // given

        // when
        Indicator indicator = target.build(
                riotApiAdaptor.getMatchIdsByPuuid(puuid),
                summonerId, puuid
        );

        // then
        assertThat(indicator.getMatchIndicators().get(0)
                .getLaneIndicator()
                .getBasicWeight()
                .getExpDiffer())
                .isEqualTo(6308 - 5928);
        assertThat(indicator.getMatchIndicators().get(0)
                .getLaneIndicator()
                .getBasicWeight()
                .getCsDiffer())
                .isEqualTo(100 - 98);
    }

    @Test
    @Order(3)
    @DisplayName("정글 라인관여 : 킬어시, 관여율 확인")
    void towerGoldDifferTest() {
        // given

        // when
        Indicator indicator = target.build(
                riotApiAdaptor.getMatchIdsByPuuid(puuid),
                summonerId, puuid
        );
        JgIndicator jgIndicator = (JgIndicator) indicator.getMatchIndicators().get(0).getLaneIndicator();
        int killAssistDiffer = jgIndicator.getLaneAssist().getKillAssistDiffer();
        int killInvolvementRate = jgIndicator.getLaneAssist().getKillInvolvementRate();

        // then
        assertThat(killAssistDiffer).isEqualTo(3 + 2 - 2 - 1);
        assertThat(killInvolvementRate).isEqualTo(3 / 8);
    }

    @Test
    @Order(4)
    @DisplayName("정글 공격적인 라인전 : 적진영 킬관여 확인")
    void solokillDifferTest() {
        // given

        // when
        Indicator indicator = target.build(
                riotApiAdaptor.getMatchIdsByPuuid(puuid),
                summonerId, puuid
        );
        JgIndicator jgIndicator = (JgIndicator) indicator.getMatchIndicators().get(0).getLaneIndicator();
        int killInvolvementInEnemyCamp = jgIndicator.getAggresiveJgAbility().getKillInvolvementInEnemyCamp();

        // then
        assertThat(killInvolvementInEnemyCamp).isEqualTo(3 - 1);
    }
}