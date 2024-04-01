package com.ssafy.matchup_statistics.global.dto.jpa;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.matchup_statistics.global.entity.RiotAccount;
import com.ssafy.matchup_statistics.global.entity.SummonerProfile;
import lombok.Data;

@Data
public class RiotAccountInfoDto {
    private String id;
    private SummonerProfile summonerProfile;
    private Long revisionDate;
    private String tier;
    private String leagueRank;
    private Integer leaguePoint;

    @QueryProjection
    public RiotAccountInfoDto(String id, SummonerProfile summonerProfile, Long revisionDate, String tier, String leagueRank, Integer leaguePoint) {
        this.id = id;
        this.summonerProfile = summonerProfile;
        this.revisionDate = revisionDate;
        this.tier = tier;
        this.leagueRank = leagueRank;
        this.leaguePoint = leaguePoint;
    }

    public RiotAccountInfoDto(RiotAccount riotAccount){
        this.id = riotAccount.getId();
        this.summonerProfile = riotAccount.getSummonerProfile();
        this.revisionDate = riotAccount.getRevisionDate();
        this.tier = riotAccount.getTier();
        this.leagueRank = riotAccount.getLeagueRank();
        this.leaguePoint = riotAccount.getLeaguePoint();
    }
}
