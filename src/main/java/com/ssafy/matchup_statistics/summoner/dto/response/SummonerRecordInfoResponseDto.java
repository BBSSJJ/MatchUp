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
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class SummonerRecordInfoResponseDto {
    private SummonerInfoResponseDto summonerInfo;
    private LeagueInfoResponseDto leagueInfo;
    private List<RecordMatchDetail> matches;
    private Record record;

    public SummonerRecordInfoResponseDto(SummonerInfoResponseDto summonerInfo, LeagueInfoResponseDto leagueInfo, List<MatchDetailResponseDto> matches, Indicator indicator) {
        this.summonerInfo = summonerInfo;
        this.leagueInfo = leagueInfo;
        this.matches = matches.stream().map(RecordMatchDetail::new).toList();
        this.record = new Record(indicator);
    }

    public SummonerRecordInfoResponseDto(Summoner summoner, Indicator indicator, List<Match> matches) {
        this.summonerInfo = new SummonerInfoResponseDto(summoner);
        this.leagueInfo = new LeagueInfoResponseDto(summoner.getLeague());
        this.record = new Record(indicator);
        this.matches = matches.stream().map(m -> new RecordMatchDetail(m.getMatchDetail())).toList();
    }

    @Data
    @NoArgsConstructor
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
            if (matchIndicatorStatistics.getMetadata().getChampionCount() != null)
                this.top3Champions = calculator.calculateMost3Champion(matchIndicatorStatistics.getMetadata().getChampionCount());
            if (matchIndicatorStatistics.getMetadata().getTeamPositionCount() != null)
                this.mostLane = calculator.calculateMostLane(matchIndicatorStatistics.getMetadata().getTeamPositionCount());
        }
    }

    @Data
    @NoArgsConstructor
    public static class RecordMatchDetail {

        public Metadata metadata = new Metadata(null);
        public Info info = new Info(null, null, null);

        public RecordMatchDetail(MatchDetailResponseDto matchDetailResponseDto) {
            this.metadata.participants = matchDetailResponseDto.getMetadata().getParticipants();
            this.info.teams = matchDetailResponseDto.getInfo().getTeams().stream().map(t -> new Team(t.isWin())).toList();
            this.info.participants = matchDetailResponseDto.getInfo().getParticipants().stream().map(p ->
                    new Participant(p.championName, p.riotIdGameName, p.puuid, p.riotIdTagline, p.summonerName, p.individualPosition, p.role, p.teamPosition, p.item0, p.item1, p.item2, p.item3, p.item4, p.item5, p.item6, p.summonerLevel, p.kills, p.deaths, p.assists, p.largestMultiKill, p.profileIcon)).toList();
            this.info.gameDuration = matchDetailResponseDto.getInfo().getGameDuration();
        }

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Participant {
            private String championName;
            private String riotIdGameName;
            private String puuid;
            private String riotIdTagline;
            private String summonerName;
            private String individualPosition;
            private String role;
            private String teamPosition;
            private Integer item0;
            private Integer item1;
            private Integer item2;
            private Integer item3;
            private Integer item4;
            private Integer item5;
            private Integer item6;
            private Integer summonerLevel;
            private Integer kills;
            private Integer deaths;
            private Integer assists;
            private Integer largestMultiKill;
            private Integer profileIcon;
        }
        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Metadata {
            private List<String> participants;
        }

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Team {
            private boolean win;
        }

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Info {
            public List<Participant> participants;
            public List<Team> teams;
            public Integer gameDuration;
        }
    }
}
