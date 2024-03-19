package com.ssafy.matchup_statistics.indicator.entity.riot.league;

import lombok.Data;

@Data
public class LeagueIndicator {
    private String tier;
    private String rank;
    private Integer wins;
    private Integer losses;
}
