package com.ssafy.matchup_statistics.global.dto.response;

import com.ssafy.matchup_statistics.summoner.entity.Summoner;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SummonerInfoResponseDto {
    private String id;
    private String puuid;
    private String accountId;
    private String name;
    private Integer profileIconId;
    private Long revisionDate;
    private Long summonerLevel;

    public SummonerInfoResponseDto(Summoner summoner) {
        if (summoner.getId() != null)
            this.id = summoner.getId();
        if (summoner.getSummonerDetail() != null && summoner.getSummonerDetail().getPuuid() != null)
            this.puuid = summoner.getSummonerDetail().getPuuid();
        if (summoner.getSummonerDetail() != null && summoner.getSummonerDetail().getPuuid() != null)
            this.accountId = summoner.getSummonerDetail().getAccountId();
        if (summoner.getSummonerDetail() != null && summoner.getSummonerDetail().getName() != null)
            this.name = summoner.getSummonerDetail().getName();
        if (summoner.getSummonerDetail() != null && summoner.getSummonerDetail().getProfileIconId() != null)
            this.profileIconId = summoner.getSummonerDetail().getProfileIconId();
        if (summoner.getSummonerDetail() != null && summoner.getSummonerDetail().getRevisionDate() != null)
            this.revisionDate = summoner.getSummonerDetail().getRevisionDate();
        if (summoner.getSummonerDetail() != null && summoner.getSummonerDetail().getSummonerLevel() != null)
            this.summonerLevel = summoner.getSummonerDetail().getSummonerLevel();
    }
}
