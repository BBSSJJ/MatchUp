package com.ssafy.matchup_statistics.global.dto.jpa;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.ssafy.matchup_statistics.global.dto.jpa.QRiotAccountInfoDto is a Querydsl Projection type for RiotAccountInfoDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QRiotAccountInfoDto extends ConstructorExpression<RiotAccountInfoDto> {

    private static final long serialVersionUID = 276333672L;

    public QRiotAccountInfoDto(com.querydsl.core.types.Expression<String> id, com.querydsl.core.types.Expression<? extends com.ssafy.matchup_statistics.global.entity.SummonerProfile> summonerProfile, com.querydsl.core.types.Expression<Long> revisionDate, com.querydsl.core.types.Expression<String> tier, com.querydsl.core.types.Expression<String> leagueRank, com.querydsl.core.types.Expression<Integer> leaguePoint) {
        super(RiotAccountInfoDto.class, new Class<?>[]{String.class, com.ssafy.matchup_statistics.global.entity.SummonerProfile.class, long.class, String.class, String.class, int.class}, id, summonerProfile, revisionDate, tier, leagueRank, leaguePoint);
    }

}

