package com.ssafy.matchup_statistics.account.entity;

import com.ssafy.matchup_statistics.global.dto.response.AccountResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private String puuid;
    private String gameName;
    private String tagLine;

    public Account(AccountResponseDto accountInfo) {
        this.puuid = accountInfo.getPuuid();
        this.gameName = accountInfo.getGameName();
        this.tagLine = accountInfo.getTagLine();
    }
}
