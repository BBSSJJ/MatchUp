package com.ssafy.matchup_statistics.indicator.entity.riot.league;

import lombok.Data;

import java.util.LinkedHashMap;

@Data
public class LeagueIndicator {
    private String tier;
    private String rank;
    private Integer wins;
    private Integer losses;

    public LeagueIndicator(LinkedHashMap<String, Object> leagueInfo) {
        this.tier = (String) leagueInfo.get("tier");
        this.rank = (String) leagueInfo.get("rank");
        this.wins = (Integer) leagueInfo.get("wins");
        this.losses = (Integer) leagueInfo.get("losses");
    }
}
