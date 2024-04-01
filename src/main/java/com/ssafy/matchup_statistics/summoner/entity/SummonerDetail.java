package com.ssafy.matchup_statistics.summoner.entity;

import com.ssafy.matchup_statistics.global.dto.response.SummonerInfoResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SummonerDetail {
    private String puuid;
    private String accountId;
    private String name;
    private Integer profileIconId;
    private Long revisionDate;
    private Long summonerLevel;

    public SummonerDetail(SummonerInfoResponseDto summonerInfoResponseDto) {
        this.puuid = summonerInfoResponseDto.getPuuid();
        this.accountId = summonerInfoResponseDto.getAccountId();
        this.name = summonerInfoResponseDto.getName();
        this.profileIconId = summonerInfoResponseDto.getProfileIconId();
        this.revisionDate = summonerInfoResponseDto.getRevisionDate();
        this.summonerLevel = summonerInfoResponseDto.getSummonerLevel();
    }
}
