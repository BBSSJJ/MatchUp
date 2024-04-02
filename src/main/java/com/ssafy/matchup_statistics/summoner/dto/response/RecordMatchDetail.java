package com.ssafy.matchup_statistics.summoner.dto.response;


import com.ssafy.matchup_statistics.global.dto.response.MatchDetailResponseDto;
import com.ssafy.matchup_statistics.indicator.util.QueueTypeMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordMatchDetail {

    public Metadata metadata = new Metadata(null);
    public Info info = new Info(null, null, null, null);

    public RecordMatchDetail(MatchDetailResponseDto matchDetailResponseDto) {
        QueueTypeMap queueTypeMap = new QueueTypeMap();
        this.metadata.participants = matchDetailResponseDto.getMetadata().getParticipants();
        this.info.teams = matchDetailResponseDto.getInfo().getTeams().stream().map(t -> new Team(t.isWin())).toList();
        this.info.participants = matchDetailResponseDto.getInfo().getParticipants().stream().map(p ->
                new Participant(p.championName, p.riotIdGameName, p.puuid, p.riotIdTagline, p.summonerName, p.individualPosition, p.role, p.teamPosition, p.item0, p.item1, p.item2, p.item3, p.item4, p.item5, p.item6, p.summonerLevel, p.kills, p.deaths, p.assists, p.largestMultiKill, p.profileIcon)).toList();
        this.info.gameDuration = matchDetailResponseDto.getInfo().getGameDuration();
        this.info.queueType = queueTypeMap.getQueueType(matchDetailResponseDto.getInfo().getQueueId());
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
        public String queueType;
    }
}