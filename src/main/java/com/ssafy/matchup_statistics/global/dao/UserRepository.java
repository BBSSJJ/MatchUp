package com.ssafy.matchup_statistics.global.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.matchup_statistics.global.dto.jpa.QRiotAccountInfoDto;
import com.ssafy.matchup_statistics.global.dto.jpa.QUserInfoDto;
import com.ssafy.matchup_statistics.global.dto.jpa.QUserRiotAccountDto;
import com.ssafy.matchup_statistics.global.dto.jpa.UserRiotAccountDto;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import static com.ssafy.matchup_statistics.global.entity.QRiotAccount.riotAccount;
import static com.ssafy.matchup_statistics.global.entity.QUser.user;

@Repository
public class UserRepository implements UserDao {

    private final JPAQueryFactory queryFactory;

    public UserRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public UserRiotAccountDto getUserRiotAccountDto(long userId) {

        return queryFactory
                .select(new QUserRiotAccountDto(
                        new QUserInfoDto(user.id),
                        new QRiotAccountInfoDto(riotAccount.id, riotAccount.summonerProfile, riotAccount.revisionDate, riotAccount.tier, riotAccount.leagueRank, riotAccount.leaguePoint)))
                .from(user)
                .leftJoin(user.riotAccount, riotAccount)
                .where(user.id.eq(userId))
                .fetchOne();
    }
}
