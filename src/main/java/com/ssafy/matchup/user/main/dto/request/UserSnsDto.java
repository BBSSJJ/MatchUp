package com.ssafy.matchup.user.main.dto.request;

import com.ssafy.matchup.user.main.dto.UserDto;
import com.ssafy.matchup.user.main.entity.type.SnsType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSnsDto {
    private UserDto userDto;
    private String snsId;
    private SnsType snsType;
}
