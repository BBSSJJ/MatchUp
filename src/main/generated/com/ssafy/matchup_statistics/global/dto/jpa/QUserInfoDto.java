package com.ssafy.matchup_statistics.global.dto.jpa;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.ssafy.matchup_statistics.global.dto.jpa.QUserInfoDto is a Querydsl Projection type for UserInfoDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QUserInfoDto extends ConstructorExpression<UserInfoDto> {

    private static final long serialVersionUID = 1885331838L;

    public QUserInfoDto(com.querydsl.core.types.Expression<Long> id) {
        super(UserInfoDto.class, new Class<?>[]{long.class}, id);
    }

}

