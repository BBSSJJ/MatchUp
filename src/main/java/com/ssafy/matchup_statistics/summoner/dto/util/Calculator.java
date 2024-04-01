package com.ssafy.matchup_statistics.summoner.dto.util;

import com.ssafy.matchup_statistics.indicator.entity.match.MatchIndicator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Calculator {
    public String[] calculateMost3Champion(LinkedHashMap<String, Integer> map) {
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(map.entrySet());
        entries.sort(Map.Entry.comparingByValue((e1, e2) -> e2 - e1));
        return new String[]{entries.get(0).getKey(), entries.get(1).getKey(), entries.get(2).getKey()};
    }

    public String calculateMostLane(LinkedHashMap<String, Integer> map) {
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(map.entrySet());
        entries.sort(Map.Entry.comparingByValue((e1, e2) -> e2 - e1));
        return entries.get(0).getKey();
    }

    public double calculateWinRate(List<MatchIndicator> matchIndicators) {
        int totalCount = 0;
        int winCount = 0;
        for (int i = 0; i < matchIndicators.size(); i++) {
            MatchIndicator matchIndicator = matchIndicators.get(i);

            // 챔피언이 선택 안된 게임 제외(15분 이전게임 Or 라인정보 없는 게임)
            if(matchIndicator.getMetadata().getChampion() != null) {
                totalCount++;
                if (matchIndicator.getMetadata().isWin()) winCount++;
            }
        }
        return (double) winCount / totalCount;
    }

    public String calculateLatestChampion(List<MatchIndicator> matchIndicators) {
        String ret = "No Champion Used";
        for (int i = 0; i < matchIndicators.size(); i++) {
            MatchIndicator matchIndicator = matchIndicators.get(i);

            // 챔피언이 선택 안된 게임 제외(15분 이전게임 Or 라인정보 없는 게임)
            if(matchIndicator.getMetadata().getChampion() != null) {
                ret = matchIndicator.getMetadata().getChampion();
            }
        }
        return  ret;
    }
}
