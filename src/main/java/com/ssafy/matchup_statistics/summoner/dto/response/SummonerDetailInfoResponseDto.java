package com.ssafy.matchup_statistics.summoner.dto.response;

import com.ssafy.matchup_statistics.indicator.entity.Indicator;
import com.ssafy.matchup_statistics.indicator.entity.match.MatchIndicatorStatistics;
import com.ssafy.matchup_statistics.indicator.entity.match.TeamPosition;
import com.ssafy.matchup_statistics.summoner.dto.util.Calculator;
import com.ssafy.matchup_statistics.summoner.entity.Summoner;
import lombok.Data;

@Data
public class SummonerDetailInfoResponseDto {
    private String rank;
    private String tier;
    private double winRate;
    private String latestChampion;
    private String[] top3Champions;
    private String mostLane;
    private int pingCountAvg;
    private double killAvg;
    private double deathAvg;
    private double assistAvg;
    private double kdaAvg;



    public SummonerDetailInfoResponseDto(Summoner summoner, Indicator indicator) {
        MatchIndicatorStatistics.Metadata metadata = indicator.getMatchIndicatorStatistics().getMetadata();
        Calculator calculator = new Calculator();

        this.rank = summoner.getLeague().getRank();
        this.tier = summoner.getLeague().getTier();
        this.winRate = calculator.calculateWinRate(indicator.getMatchIndicators());
        this.latestChampion = calculator.calculateLatestChampion(indicator.getMatchIndicators());
        if (metadata.getTeamPositionCount() != null) this.mostLane = calculator.calculateMostLane(metadata.getTeamPositionCount());
        if (metadata.getChampionCount() != null) this.top3Champions = calculator.calculateMost3Champion(metadata.getChampionCount());
        this.killAvg = metadata.getKillAvg();
        this.deathAvg = metadata.getDeathAvg();
        this.assistAvg = metadata.getAssistAvg();
        this.kdaAvg = metadata.getKdaAvg();
    }
}
