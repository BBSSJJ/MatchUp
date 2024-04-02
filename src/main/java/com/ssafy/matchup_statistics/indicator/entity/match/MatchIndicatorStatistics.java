package com.ssafy.matchup_statistics.indicator.entity.match;

import com.ssafy.matchup_statistics.indicator.entity.match.beginning.LaneIndicator;
import com.ssafy.matchup_statistics.indicator.entity.match.beginning.LaneIndicatorStatistics;
import com.ssafy.matchup_statistics.indicator.entity.match.end.MacroIndicator;
import com.ssafy.matchup_statistics.indicator.entity.match.end.MacroIndicatorStatistics;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Getter
@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchIndicatorStatistics {

    private LaneIndicatorStatistics laneIndicatorAvg;
    private MacroIndicatorStatistics macroIndicatorAvg;
    private Metadata metadata;

    public MatchIndicatorStatistics(List<MatchIndicator> matchIndicators) {
        List<LaneIndicator> laneIndicators = matchIndicators.stream().map(MatchIndicator::getLaneIndicator).toList();
        List<MacroIndicator> macroIndicators = matchIndicators.stream().map(MatchIndicator::getMacroIndicator).toList();
        log.debug("{}개 라인통계 평균 생성시작", laneIndicators);
        if (!laneIndicators.isEmpty()) this.laneIndicatorAvg = new LaneIndicatorStatistics(laneIndicators);
        log.debug("{}개 운영통계 평균 생성시작", macroIndicators);
        if (!macroIndicators.isEmpty()) this.macroIndicatorAvg = new MacroIndicatorStatistics(macroIndicators);
        this.metadata = new Metadata(matchIndicators);
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Metadata {
        private int totalCount;
        private LinkedHashMap<String, Integer> teamPositionCount = new LinkedHashMap<>();
        private LinkedHashMap<String, Integer> championCount = new LinkedHashMap<>();
        private List<String> matchIds = new ArrayList<>();
        private long timeDurationAvg;
        private int isFinishedBeforeFifteenCount;
        private int isOurTeamEarlySurrenderedCount;
        private int winCount;
        private int pingCountAvg;
        private double killAvg;
        private double deathAvg;
        private double assistAvg;
        private double kdaAvg;

        public Metadata(List<MatchIndicator> matchIndicators) {
            matchIndicators.forEach(matchIndicator -> {
                totalCount++;
                if (matchIndicator.getMetadata().getLaneInfo().getTeamPosition() != null) {
                    updateMap(matchIndicator.getMetadata().getLaneInfo().getTeamPosition().toString(), teamPositionCount);
                }
                if (matchIndicator.getMetadata().getChampion() != null) {
                    updateMap(matchIndicator.getMetadata().getChampion(), championCount);
                }
                matchIds.add(matchIndicator.getMatchId());
                timeDurationAvg += matchIndicator.getMetadata().getTimeInfo().getGameDuration();
                if (matchIndicator.getMetadata().isFinishedBeforeFifteen()) isFinishedBeforeFifteenCount++;
                if (matchIndicator.getMetadata().isOurTeamEarlySurrendered()) isOurTeamEarlySurrenderedCount++;
                if (matchIndicator.getMetadata().isWin()) winCount++;

                pingCountAvg += matchIndicator.getMetadata().getPingCount();
                killAvg += matchIndicator.getMetadata().getKill();
                deathAvg += matchIndicator.getMetadata().getDeath();
                assistAvg += matchIndicator.getMetadata().getAssist();
                kdaAvg += matchIndicator.getMetadata().getKda();
            });

            timeDurationAvg /= totalCount;
            pingCountAvg /= totalCount;
            killAvg /= totalCount;
            deathAvg /= totalCount;
            assistAvg /= totalCount;
            kdaAvg /= totalCount;
        }

        private void updateMap(String key, LinkedHashMap<String, Integer> map) {
            if (map.containsKey(key)) {
                map.put(key, map.get(key) + 1);
            } else {
                map.put(key, 1);
            }
        }
    }
}
