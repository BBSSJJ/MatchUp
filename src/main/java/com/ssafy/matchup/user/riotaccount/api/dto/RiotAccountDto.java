package com.ssafy.matchup.user.riotaccount.api.dto;

import com.ssafy.matchup.user.riotaccount.entity.RiotAccount;
import com.ssafy.matchup.user.riotaccount.entity.SummonerProfile;
import lombok.Builder;
import lombok.Data;

@Data
public class RiotAccountDto {
    private String riotTokenValue;
    private SummonerProfile summonerProfile;
    private String tier;
    private String leagueRank;
    private String leaguePoint;

    @Builder
    public RiotAccountDto(RiotAccount riotAccount) {
        this.riotTokenValue = riotAccount.getRiotTokenValue();
        this.summonerProfile = riotAccount.getSummonerProfile();
        this.tier = riotAccount.getTier();
        this.leagueRank = riotAccount.getLeagueRank();
        this.leaguePoint = riotAccount.getLeaguePoint();
    }
}
