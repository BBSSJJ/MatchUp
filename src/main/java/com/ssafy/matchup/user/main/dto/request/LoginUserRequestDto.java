package com.ssafy.matchup.user.main.dto.request;

import com.ssafy.matchup.user.main.entity.type.SnsType;
import lombok.Data;

@Data
public class LoginUserRequestDto {
    private SnsType snsType;
    private String snsId;
}
