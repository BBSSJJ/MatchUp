package com.ssafy.matchup.user.main.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.matchup.user.main.dto.request.LoginUserRequestDto;
import com.ssafy.matchup.user.main.entity.Setting;
import com.ssafy.matchup.user.main.entity.User;
import com.ssafy.matchup.user.main.entity.type.AuthorityType;
import com.ssafy.matchup.user.main.entity.type.SnsType;
import com.ssafy.matchup.user.riotaccount.api.dto.RiotAccountDto;
import com.ssafy.matchup.user.riotaccount.entity.RiotAccount;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserDto {
    private Long userId;
    private AuthorityType role;
    private RiotAccountDto riotAccount;


    @Builder
    public UserDto(User user) {
        this.userId = user.getId();
        this.role = user.getRole();
        this.riotAccount = new RiotAccountDto(user.getRiotAccount());
    }

    @QueryProjection
    public UserDto(User user, RiotAccount riotAccount) {
        this.userId = user.getId();
        this.role = user.getRole();
        this.riotAccount = new RiotAccountDto(riotAccount);
    }
}
