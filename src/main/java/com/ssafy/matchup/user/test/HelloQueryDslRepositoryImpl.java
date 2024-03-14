package com.ssafy.matchup.user.test;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

public class HelloQueryDslRepositoryImpl implements HelloQueryDslRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public HelloQueryDslRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<HelloQueryDto> search() {
        return queryFactory
                .select(new QHelloQueryDto(
                        QHelloQueryDsl.helloQueryDsl.name,
                        QHelloQueryDsl.helloQueryDsl.age))
                .from(QHelloQueryDsl.helloQueryDsl)
                .fetch();
    }
}
