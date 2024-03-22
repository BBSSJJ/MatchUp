package com.ssafy.matchup.user.main.dto.request;

import com.ssafy.matchup.user.main.entity.type.SnsType;
import lombok.Data;

@Data
public class RegistUserRequestDto {
    private SnsType snsType;
    private String snsId;
    private String name;
}
