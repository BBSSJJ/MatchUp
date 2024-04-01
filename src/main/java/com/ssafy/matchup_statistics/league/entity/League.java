package com.ssafy.matchup_statistics.league.entity;

import com.ssafy.matchup_statistics.global.dto.response.LeagueInfoResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class League {
    private String leagueId;
    private String queueType;
    private String tier;
    private String rank;
    private String summonerId;
    private String summonerName;
    private int leaguePoints;
    private int wins;
    private int losses;
    private boolean veteran;
    private boolean inactive;
    private boolean freshBlood;
    private boolean hotStreak;

    public League(LeagueInfoResponseDto league) {
        this.leagueId = league.getLeagueId();
        this.queueType = league.getQueueType();
        this.tier = league.getTier();
        this.rank = league.getRank();
        this.summonerId = league.getSummonerId();
        this.summonerName = league.getSummonerName();
        this.leaguePoints = league.getLeaguePoints();
        this.wins = league.getWins();
        this.losses = league.getLosses();
        this.veteran = league.isVeteran();
        this.inactive = league.isInactive();
        this.freshBlood = league.isFreshBlood();
        this.hotStreak = league.isHotStreak();
    }
}
