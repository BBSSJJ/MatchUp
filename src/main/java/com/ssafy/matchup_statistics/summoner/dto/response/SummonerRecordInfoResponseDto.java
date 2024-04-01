package com.ssafy.matchup_statistics.summoner.dto.response;

import com.ssafy.matchup_statistics.global.dto.response.LeagueInfoResponseDto;
import com.ssafy.matchup_statistics.global.dto.response.MatchDetailResponseDto;
import com.ssafy.matchup_statistics.global.dto.response.SummonerInfoResponseDto;
import com.ssafy.matchup_statistics.indicator.entity.Indicator;
import com.ssafy.matchup_statistics.indicator.entity.match.MatchIndicatorStatistics;
import com.ssafy.matchup_statistics.match.entity.Match;
import com.ssafy.matchup_statistics.summoner.dto.util.Calculator;
import com.ssafy.matchup_statistics.summoner.entity.Summoner;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SummonerRecordInfoResponseDto {
    private SummonerInfoResponseDto summonerInfo;
    private LeagueInfoResponseDto leagueInfo;
    private List<MatchDetailResponseDto> matches;
    private Record record;

    public SummonerRecordInfoResponseDto(SummonerInfoResponseDto summonerInfo, LeagueInfoResponseDto leagueInfo, List<MatchDetailResponseDto> matches, Indicator indicator) {
        this.summonerInfo = summonerInfo;
        this.leagueInfo = leagueInfo;
        this.matches = matches;
        this.record = new Record(indicator);
    }

    public SummonerRecordInfoResponseDto(Summoner summoner, Indicator indicator, List<Match> matches) {
        this.summonerInfo = new SummonerInfoResponseDto(summoner);
        this.leagueInfo = new LeagueInfoResponseDto(summoner.getLeague());
        this.record = new Record(indicator);
        this.matches = matches.stream().map(Match::getMatchDetail).toList();
        // TODO : 리그 정보 어떻게 받아올건지 궁리
    }

    @Data
    public static class Record {
        private double winRate;
        private String latestChampion;
        private String[] top3Champions = new String[3];
        private String mostLane;

        public Record(Indicator indicator) {
            Calculator calculator = new Calculator();
            MatchIndicatorStatistics matchIndicatorStatistics = indicator.getMatchIndicatorStatistics();

            this.winRate = calculator.calculateWinRate(indicator.getMatchIndicators());
            this.latestChampion = calculator.calculateLatestChampion(indicator.getMatchIndicators());
            if (matchIndicatorStatistics.getMetadata().getChampionCount() != null) this.top3Champions = calculator.calculateMost3Champion(matchIndicatorStatistics.getMetadata().getChampionCount());
            if (matchIndicatorStatistics.getMetadata().getTeamPositionCount() != null) this.mostLane = calculator.calculateMostLane(matchIndicatorStatistics.getMetadata().getTeamPositionCount());
        }
    }
}
