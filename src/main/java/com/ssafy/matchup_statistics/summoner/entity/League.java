package com.ssafy.matchup_statistics.summoner.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "league")
@Builder
@AllArgsConstructor
@Getter
public class League {
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
