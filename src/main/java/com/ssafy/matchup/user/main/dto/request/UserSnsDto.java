package com.ssafy.matchup.user.main.dto.request;

import com.ssafy.matchup.user.main.dto.UserDto;
import com.ssafy.matchup.user.main.entity.type.AuthorityType;
import com.ssafy.matchup.user.main.entity.type.SnsType;
import com.ssafy.matchup.user.riotaccount.api.dto.RiotAccountDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSnsDto {
    private Long userId;
    private AuthorityType role;
    private RiotAccountDto riotAccount;
    private String snsId;
    private SnsType snsType;

    public UserSnsDto(UserDto userDto, String snsId, SnsType snsType) {
        this.userId = userDto.getUserId();
        this.role = userDto.getRole();
        this.riotAccount = userDto.getRiotAccount();
        this.snsId = snsId;
        this.snsType = snsType;
    }
}
