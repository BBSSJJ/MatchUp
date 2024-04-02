package com.ssafy.matchup_statistics.global.dto.jpa;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.ssafy.matchup_statistics.global.dto.jpa.QUserRiotAccountDto is a Querydsl Projection type for UserRiotAccountDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QUserRiotAccountDto extends ConstructorExpression<UserRiotAccountDto> {

    private static final long serialVersionUID = -540991455L;

    public QUserRiotAccountDto(com.querydsl.core.types.Expression<? extends UserInfoDto> userInfo, com.querydsl.core.types.Expression<? extends RiotAccountInfoDto> riotAccountInfo) {
        super(UserRiotAccountDto.class, new Class<?>[]{UserInfoDto.class, RiotAccountInfoDto.class}, userInfo, riotAccountInfo);
    }

}

