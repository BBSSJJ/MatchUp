package com.ssafy.matchup_statistics.global.dto.response;

import com.ssafy.matchup_statistics.account.entity.Account;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountResponseDto {
    private String puuid;
    private String gameName;
    private String tagLine;

    public AccountResponseDto(Account account) {
        this.puuid = account.getPuuid();
        this.gameName = account.getGameName();
        this.tagLine = account.getTagLine();
    }
}
