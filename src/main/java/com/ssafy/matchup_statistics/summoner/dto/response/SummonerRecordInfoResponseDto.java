package com.ssafy.matchup_statistics.summoner.dto.response;

import com.ssafy.matchup_statistics.global.dto.response.LeagueInfoResponseDto;
import com.ssafy.matchup_statistics.global.dto.response.MatchDetailResponseDto;
import com.ssafy.matchup_statistics.global.dto.response.SummonerInfoResponseDto;
import com.ssafy.matchup_statistics.indicator.entity.Indicator;
import com.ssafy.matchup_statistics.indicator.entity.match.MatchIndicatorStatistics;
import com.ssafy.matchup_statistics.indicator.util.QueueTypeMap;
import com.ssafy.matchup_statistics.match.entity.Match;
import com.ssafy.matchup_statistics.indicator.util.Calculator;
import com.ssafy.matchup_statistics.summoner.entity.Summoner;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class SummonerRecordInfoResponseDto {
    private SummonerInfoResponseDto summonerInfo;
    private LeagueInfoResponseDto leagueInfo;
    private List<RecordMatchDetail> matches;
    private SummonerDetailInfoResponseDto record;

    public SummonerRecordInfoResponseDto(SummonerInfoResponseDto summonerInfo, LeagueInfoResponseDto leagueInfo, List<MatchDetailResponseDto> matches, Indicator indicator) {
        this.summonerInfo = summonerInfo;
        this.leagueInfo = leagueInfo;
        this.matches = matches.stream().map(RecordMatchDetail::new).toList();
        this.record = new SummonerDetailInfoResponseDto(leagueInfo.getRank(), leagueInfo.getTier(), indicator);
    }

    public SummonerRecordInfoResponseDto(Summoner summoner, Indicator indicator, List<Match> matches) {
        this.summonerInfo = new SummonerInfoResponseDto(summoner);
        this.leagueInfo = new LeagueInfoResponseDto(summoner.getLeague());
        this.matches = matches.stream().map(Match::getMatchDetail).toList();
        this.record = new SummonerDetailInfoResponseDto(summoner.getLeague().getRank(), summoner.getLeague().getTier(), indicator);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Record {
        private double winRate;
        private String latestChampion;
        private String[] top3Champions = new String[3];
        private String mostLane;

        public Record(Indicator indicator) {
            Calculator calculator = new Calculator();
            log.info("Indicator : {}", indicator);
            log.info("matchIndicator : {}", indicator.getMatchIndicators());
            MatchIndicatorStatistics matchIndicatorStatistics = indicator.getMatchIndicatorStatistics();

            this.winRate = calculator.calculateWinRate(indicator.getMatchIndicators());
            this.latestChampion = calculator.calculateLatestChampion(indicator.getMatchIndicators());
            if (matchIndicatorStatistics.getMetadata() != null && matchIndicatorStatistics.getMetadata().getChampionCount() != null)
                this.top3Champions = calculator.calculateMost3Champion(matchIndicatorStatistics.getMetadata().getChampionCount());
            if (matchIndicatorStatistics.getMetadata() != null && matchIndicatorStatistics.getMetadata().getTeamPositionCount() != null)
                this.mostLane = calculator.calculateMostLane(matchIndicatorStatistics.getMetadata().getTeamPositionCount());
        }
    }
}
