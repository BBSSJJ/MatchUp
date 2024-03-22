package com.ssafy.matchup_statistics.indicator.entity.riot.league;

import com.ssafy.matchup_statistics.league.dto.response.LeagueInfoResponseDto;
import lombok.Data;

@Data
public class LeagueIndicator {
    private String tier;
    private String rank;
    private Integer wins;
    private Integer losses;

    public LeagueIndicator(LeagueInfoResponseDto leagueInfo) {
        this.tier = leagueInfo.getTier();
        this.rank = leagueInfo.getRank();
        this.wins = leagueInfo.getWins();
        this.losses = leagueInfo.getLosses();
    }
}
