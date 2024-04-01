package com.ssafy.matchup_statistics.global.dto.response;

import com.ssafy.matchup_statistics.league.entity.League;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedHashMap;

@Data
@AllArgsConstructor
public class LeagueInfoResponseDto {
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

    public LeagueInfoResponseDto(League league) {
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

    public LeagueInfoResponseDto(LinkedHashMap<String, Object> response) {
        this.leagueId = (String) response.get("leagueId");
        this.queueType = (String) response.get("queueType");
        this.tier = (String) response.get("tier");
        this.rank = (String) response.get("rank");
        this.summonerId = (String) response.get("summonerId");
        this.summonerName = (String) response.get("summonerName");
        this.leaguePoints = (Integer) response.get("leaguePoints");
        this.wins = (Integer) response.get("wins");
        this.losses = (Integer) response.get("losses");
        this.veteran = (Boolean) response.get("veteran");
        this.inactive = (Boolean) response.get("inactive");
        this.freshBlood = (Boolean) response.get("freshBlood");
        this.hotStreak = (Boolean) response.get("hotStreak");
    }

    public LeagueInfoResponseDto() {
        this.leagueId = "no league data";
        this.queueType = "no league data";
        this.tier = "no league data";
        this.rank = "no league data";
        this.summonerId = "no league data";
        this.summonerName = "no league data";
    }
}
