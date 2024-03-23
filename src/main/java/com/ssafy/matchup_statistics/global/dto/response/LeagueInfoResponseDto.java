package com.ssafy.matchup_statistics.global.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;

@Data
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
