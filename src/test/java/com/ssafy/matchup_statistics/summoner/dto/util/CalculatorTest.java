package com.ssafy.matchup_statistics.summoner.dto.util;

import com.ssafy.matchup_statistics.indicator.entity.Indicator;
import com.ssafy.matchup_statistics.indicator.entity.match.MatchIndicator;
import com.ssafy.matchup_statistics.indicator.entity.match.MatchIndicatorStatistics;
import com.ssafy.matchup_statistics.indicator.entity.match.TeamPosition;
import com.ssafy.matchup_statistics.indicator.entity.match.beginning.LaneIndicatorStatistics;
import com.ssafy.matchup_statistics.indicator.entity.match.end.MacroIndicatorStatistics;
import com.ssafy.matchup_statistics.summoner.dto.response.SummonerRecordInfoResponseDto;
import com.ssafy.matchup_statistics.indicator.util.Calculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CalculatorTest {

    @Test
    @DisplayName("탑3 정렬 테스트")
    void top3SortTest() {
        // given
        LinkedHashMap<String, Integer> championCount = new LinkedHashMap<>();
        championCount.put("a", 4);
        championCount.put("b", 1);
        championCount.put("c", 3);
        championCount.put("d", 2);
        championCount.put("e", 5);
        MatchIndicatorStatistics.Metadata metadata = MatchIndicatorStatistics.Metadata.builder().championCount(championCount).build();
        MatchIndicatorStatistics matchIndicatorStatistics =
                new MatchIndicatorStatistics(new LaneIndicatorStatistics(), new MacroIndicatorStatistics(), metadata);
        Indicator indicator = Indicator.builder()
                .matchIndicatorStatistics(matchIndicatorStatistics)
                .build();
        // when
        SummonerRecordInfoResponseDto summonerRecordInfoResponseDto = new SummonerRecordInfoResponseDto(null, null, null, indicator);
        String[] top3 = summonerRecordInfoResponseDto.getRecord().getTop3Champions();

        // then
        assertThat(top3[0]).isEqualTo("e");
        assertThat(top3[1]).isEqualTo("a");
        assertThat(top3[2]).isEqualTo("c");
    }

    @Test
    @DisplayName("가장 많이 가는 라인 테스트")
    void mostLaneTest() {
        // given
        LinkedHashMap<String, Integer> teamPositionCount = new LinkedHashMap<>();
        teamPositionCount.put(TeamPosition.TOP.name(), 4);
        teamPositionCount.put(TeamPosition.MIDDLE.name(), 1);
        teamPositionCount.put(TeamPosition.JUNGLE.name(), 3);
        teamPositionCount.put(TeamPosition.BOTTOM.name(), 2);
        teamPositionCount.put(TeamPosition.UTILITY.name(), 5);
        MatchIndicatorStatistics.Metadata metadata = MatchIndicatorStatistics.Metadata.builder().teamPositionCount(teamPositionCount).build();
        MatchIndicatorStatistics matchIndicatorStatistics =
                new MatchIndicatorStatistics(new LaneIndicatorStatistics(), new MacroIndicatorStatistics(), metadata);
        Indicator indicator = Indicator.builder()
                .matchIndicatorStatistics(matchIndicatorStatistics)
                .build();


        // when
        SummonerRecordInfoResponseDto summonerRecordInfoResponseDto = new SummonerRecordInfoResponseDto(null, null, null, indicator);
        String mostLane = summonerRecordInfoResponseDto.getRecord().getMostLane();

        // then
        assertThat(mostLane).isEqualTo(TeamPosition.UTILITY.name());
    }

    @Test
    @DisplayName("승률 계산 테스트")
    void winRateTest() {
        // given
        List<MatchIndicator> matchIndicators = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            matchIndicators.add(MatchIndicator.builder()
                    .metadata(MatchIndicator.Metadata.builder()
                            .isWin(i % 3 == 0)
                            .champion("test")
                            .build())
                    .build());
        }

        // when
        Calculator calculator = new Calculator();
        double winRate = calculator.calculateWinRate(matchIndicators);

        // then
        assertThat(0 % 3 == 0).isEqualTo(true);
        assertThat(winRate).isEqualTo((double) 7 / 20);
    }

    @Test
    @DisplayName("가장 최근에 사용한 챔피언 확인 테스트 : 기본 테스트")
    void latestUseChampionTest() {
        // given
        List<MatchIndicator> matchIndicators = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            matchIndicators.add(MatchIndicator.builder()
                    .metadata(MatchIndicator.Metadata.builder()
                            .champion("test" + i)
                            .build())
                    .build());
        }

        // when
        Calculator calculator = new Calculator();
        String latestChampion = calculator.calculateLatestChampion(matchIndicators);

        // then
        assertThat(latestChampion).isEqualTo("test19");
    }


    @Test
    @DisplayName("가장 최근에 사용한 챔피언 확인 테스트 : 중간에 빈 값이 있을때")
    void latestUseChampionTest2() {
        // given
        List<MatchIndicator> matchIndicators = new ArrayList<>();
        for (int i = 0; i < 19; i++) {
            matchIndicators.add(MatchIndicator.builder()
                    .metadata(MatchIndicator.Metadata.builder()
                            .champion("test" + i)
                            .build())
                    .build());
        }
        matchIndicators.add(MatchIndicator.builder()
                .metadata(MatchIndicator.Metadata.builder()
                        .champion(null)
                        .build())
                .build());

        // when
        Calculator calculator = new Calculator();
        String latestChampion = calculator.calculateLatestChampion(matchIndicators);

        // then
        assertThat(latestChampion).isEqualTo("test18");
    }

    @Test
    @DisplayName("가장 최근에 사용한 챔피언 확인 테스트 : 한 챔피언도 하지 않았을 때")
    void latestUseChampionTest3() {
        // given
        List<MatchIndicator> matchIndicators = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            matchIndicators.add(MatchIndicator.builder()
                    .metadata(MatchIndicator.Metadata.builder()
                            .champion(null)
                            .build())
                    .build());
        }

        // when
        Calculator calculator = new Calculator();
        String latestChampion = calculator.calculateLatestChampion(matchIndicators);

        // then
        assertThat(latestChampion).isEqualTo("No Champion Used");
    }
}