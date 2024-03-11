package com.ssafy.matchup_statistics.summoner.api.dto.response;

import lombok.Data;

@Data
public class LeagueInfoResponseDto {
    private String leagueId;
    private String queueType;
    private String tier;
    private String rank;
    private String summonerId;
    private String summonerName;
    private String leaguePoints;
    private String wins;
    private String losses;
    private String veteran;
    private String inactive;
    private String freshBlood;
    private String hotStreak;
}
