package com.ssafy.matchup_statistics.summoner.dto.response;

import com.ssafy.matchup_statistics.indicator.entity.Indicator;
import com.ssafy.matchup_statistics.indicator.entity.match.MatchIndicatorStatistics;
import com.ssafy.matchup_statistics.indicator.util.Calculator;
import com.ssafy.matchup_statistics.summoner.entity.Summoner;
import lombok.Data;

@Data
public class SummonerDetailInfoResponseDto {
    private String rank;
    private String tier;
    private double winRate;
    private Integer win;
    private Integer lose;
    private String latestChampion;
    private String[] top3Champions;
    private String mostLane;
    private double pingCountAvg;
    private double killAvg;
    private double deathAvg;
    private double assistAvg;
    private double kdaAvg;


    public SummonerDetailInfoResponseDto(String rank, String tier, Indicator indicator) {
        Calculator calculator = new Calculator();

        this.rank = rank;
        this.tier = tier;

        MatchIndicatorStatistics.Metadata metadata = indicator.getMatchIndicatorStatistics().getMetadata();
        if (metadata == null) {
            this.winRate = 0;
            this.win = 0;
            this.lose = 0;
            this.latestChampion = "최근 플레이한 챔피언 없음";
            this.top3Champions = new String[]{"챔피언 없음", "챔피언 없음", "챔피언 없음"};
            this.mostLane = "최근 플레이한 게임 없음";
            this.pingCountAvg = 0;
            this.killAvg = 0;
            this.deathAvg = 0;
            this.assistAvg = 0;
            this.kdaAvg = 0;
        } else {
            this.winRate = calculator.calculateWinRate(indicator.getMatchIndicators());
            this.latestChampion = calculator.calculateLatestChampion(indicator.getMatchIndicators());
            if (metadata.getTeamPositionCount() != null)
                this.mostLane = calculator.calculateMostLane(metadata.getTeamPositionCount());
            if (metadata.getChampionCount() != null)
                this.top3Champions = calculator.calculateMost3Champion(metadata.getChampionCount());
            this.pingCountAvg = metadata.getPingCountAvg();
            this.win = metadata.getWinCount();
            this.lose = metadata.getTotalCount() - metadata.getWinCount();
            this.killAvg = metadata.getKillAvg();
            this.deathAvg = metadata.getDeathAvg();
            this.assistAvg = metadata.getAssistAvg();
            this.kdaAvg = metadata.getKdaAvg();
        }
    }
}
