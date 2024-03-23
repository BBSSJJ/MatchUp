package com.ssafy.matchup_statistics.account.entity;

import com.ssafy.matchup_statistics.account.dto.response.AccountResponseDto;
import lombok.Getter;

@Getter
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
