package com.ssafy.matchup.user.main.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.matchup.user.main.dto.QUserDto;
import com.ssafy.matchup.user.main.dto.UserDto;
import com.ssafy.matchup.user.main.entity.User;
import jakarta.persistence.EntityManager;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.matchup.user.main.entity.QUser.user;
import static com.ssafy.matchup.user.riotaccount.entity.QRiotAccount.riotAccount;

@Repository
public class UserCustomRepositoryImpl implements UserCustomRepository {
    private final JPAQueryFactory queryFactory;

    public UserCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<User> findByTierAndLeagueRankAndUseMike(List<Pair<String, String>> tierLeagueRankList, Boolean useMike) {
        BooleanExpression predicate = null;
        for (Pair<String, String> pair : tierLeagueRankList) {
            BooleanExpression condition = riotAccount.tier.eq(pair.getLeft())
                    .and(riotAccount.leagueRank.eq(pair.getRight()));
            predicate = (predicate != null) ? predicate.or(condition) : condition;
            predicate = (predicate != null) ? predicate.and(user.setting.useMike.eq(useMike)) : user.setting.useMike.eq(useMike);

        }

        return queryFactory.selectFrom(user)
                .where(predicate)
                .fetch();
    }

    @Override
    public List<UserDto> findUserByKeyword(String keyword) {
        return queryFactory.select(new QUserDto(user, riotAccount))
                .from(user)
                .leftJoin(riotAccount).fetchJoin()
                .where(user.riotAccount.summonerProfile.name.contains(keyword))
                .fetch();
    }
}
