package com.ssafy.matchup_statistics.indicator.service.builder.lane;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.ssafy.matchup_statistics.global.config.TestConfiguration;
import com.ssafy.matchup_statistics.indicator.entity.match.LaneInfo;
import com.ssafy.matchup_statistics.indicator.entity.match.MatchIndicator;
import com.ssafy.matchup_statistics.indicator.entity.match.TeamPosition;
import com.ssafy.matchup_statistics.indicator.service.builder.MatchIndicatorBuilder;
import com.ssafy.matchup_statistics.global.api.MatchRestApi;
import com.ssafy.matchup_statistics.global.dto.response.MatchDetailResponseDto;
import com.ssafy.matchup_statistics.global.dto.response.MatchTimelineResponseDto;
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
class MatchMidIndicatorBuilderTest {
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
        // 본인 아이디 : 3
        // 상대 아이디 : 8
        // 라인 : 미드
        List<String> matches = new ArrayList<>();
        matches.add("KR_6987867218");
        String puuid = "Gacb6Uc2aGHT122L0LgG9FWVMmj1TkHYWrWmqlpwlKKjs5n8IA0NbXUCtYAw8DRyoyziHcTUmnxYmQ";
        given(matchRestApi.getMatchesResponseDtoByPuuid(puuid)).willReturn(matches);
        given(matchRestApi.getMatchDetailResponseDtoByMatchId("KR_6987867218")).willReturn(matchDetailResponseDto);
        given(matchRestApi.getMatchTimelineResponseDtoByMatchId("KR_6987867218")).willReturn(matchTimelineResponseDto);

        log.info("log test");
        log.debug("log test");
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
        String puuid = "Gacb6Uc2aGHT122L0LgG9FWVMmj1TkHYWrWmqlpwlKKjs5n8IA0NbXUCtYAw8DRyoyziHcTUmnxYmQ";

        // when
        List<MatchIndicator> matchIndicators = target.buildMatches(puuid);

        // then
        assertThat(matchIndicators.get(0)
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
        String puuid = "Gacb6Uc2aGHT122L0LgG9FWVMmj1TkHYWrWmqlpwlKKjs5n8IA0NbXUCtYAw8DRyoyziHcTUmnxYmQ";

        // when
        List<MatchIndicator> matchIndicators = target.buildMatches(puuid);

        // then
        assertThat(matchIndicators.get(0)
                .getLaneIndicator()
                .getBasicWeight()
                .getExpDiffer())
                .isEqualTo(7913 - 6895);
        assertThat(matchIndicators.get(0)
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
        String puuid = "Gacb6Uc2aGHT122L0LgG9FWVMmj1TkHYWrWmqlpwlKKjs5n8IA0NbXUCtYAw8DRyoyziHcTUmnxYmQ";

        // when
        List<MatchIndicator> matchIndicators = target.buildMatches(puuid);

        // then
        assertThat(matchIndicators.get(0)
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
        String puuid = "Gacb6Uc2aGHT122L0LgG9FWVMmj1TkHYWrWmqlpwlKKjs5n8IA0NbXUCtYAw8DRyoyziHcTUmnxYmQ";

        // when
        List<MatchIndicator> matchIndicators = target.buildMatches(puuid);

        // then
        assertThat(matchIndicators.get(0)
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
        String puuid = "Gacb6Uc2aGHT122L0LgG9FWVMmj1TkHYWrWmqlpwlKKjs5n8IA0NbXUCtYAw8DRyoyziHcTUmnxYmQ";

        // when
        List<MatchIndicator> matchIndicators = target.buildMatches(puuid);

        // then
        assertThat(matchIndicators.get(0)
                .getLaneIndicator()
                .getAggresiveLaneAbilility()
                .getDealDiffer())
                .isEqualTo(9578 - 7038);
    }
}