package com.ssafy.matchup_statistics.global.dto.jpa;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class UserInfoDto {
    private long id;

    @QueryProjection
    public UserInfoDto(long id) {
        this.id = id;
    }
}
