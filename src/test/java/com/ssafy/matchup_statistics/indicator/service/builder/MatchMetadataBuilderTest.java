package com.ssafy.matchup_statistics.indicator.service.builder;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.ssafy.matchup_statistics.global.api.RiotApiAdaptor;
import com.ssafy.matchup_statistics.global.config.TestConfiguration;
import com.ssafy.matchup_statistics.global.dto.response.MatchDetailResponseDto;
import com.ssafy.matchup_statistics.global.dto.response.MatchTimelineResponseDto;
import com.ssafy.matchup_statistics.indicator.entity.Indicator;
import com.ssafy.matchup_statistics.indicator.entity.match.LaneInfo;
import com.ssafy.matchup_statistics.indicator.entity.match.MatchIndicator;
import com.ssafy.matchup_statistics.indicator.entity.match.TeamPosition;
import com.ssafy.matchup_statistics.global.api.MatchRestApi;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
@Tag("MatchMetadataBuilderTest")
class MatchMetadataBuilderTest {

    private final int DEFAULT_ROUND_UP = 100_000;
    @Mock
    RiotApiAdaptor riotApiAdaptor;
    @Mock
    MatchSaveService matchSaveService;
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
        target = new IndicatorBuilder(riotApiAdaptor, matchSaveService);

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
                .isEqualTo(laneInfo);
    }

    @Test
    @Order(2)
    @DisplayName("메타데이터 잘 가져오는지 테스트")
    void metadataTest() {
        // given
        // 메타정보 빌드
        metadata = MatchIndicator.Metadata.builder()
                .laneInfo(laneInfo)
                .isFinishedBeforeFifteen(false)
                .isOurTeamEarlySurrendered(false)
                .pingCount(1)
                .build();

        // when
        Indicator indicator = target.build(
                riotApiAdaptor.getMatchIdsByPuuid(puuid),
                summonerId, puuid
        );

        // then
        assertThat(indicator.getMatchIndicators().get(0)
                .getMetadata())
                .isEqualTo(metadata);
    }

}