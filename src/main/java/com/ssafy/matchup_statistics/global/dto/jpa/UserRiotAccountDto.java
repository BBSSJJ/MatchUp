package com.ssafy.matchup_statistics.global.dto.jpa;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class UserRiotAccountDto {
    private UserInfoDto userInfo;
    private RiotAccountInfoDto riotAccountInfo;

    @QueryProjection
    public UserRiotAccountDto(UserInfoDto userInfo, RiotAccountInfoDto riotAccountInfo) {
        this.userInfo = userInfo;
        this.riotAccountInfo = riotAccountInfo;
    }
}
