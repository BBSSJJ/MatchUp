package com.ssafy.matchup.user.riotaccount.api.dto;

import com.ssafy.matchup.user.riotaccount.entity.RiotAccount;
import com.ssafy.matchup.user.riotaccount.entity.SummonerProfile;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RiotAccountDto {
    private String id;
    private SummonerProfile summonerProfile;
    private String tier;
    private String leagueRank;
    private Integer leaguePoint;

    @Builder
    public RiotAccountDto(RiotAccount riotAccount) {
        this.id = riotAccount.getId();
        this.summonerProfile = SummonerProfile.builder()
                .name(riotAccount.getSummonerProfile().getName())
                .iconUrl(riotAccount.getSummonerProfile().getIconUrl())
                .level(riotAccount.getSummonerProfile().getLevel())
                .tag(riotAccount.getSummonerProfile().getTag())
                .build();
        this.tier = riotAccount.getTier();
        this.leagueRank = riotAccount.getLeagueRank();
        this.leaguePoint = riotAccount.getLeaguePoint();
    }
}
