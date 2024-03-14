package com.ssafy.matchup.user.test;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class HelloQueryDslTest {
    @Autowired
    EntityManager em;

    @Autowired
    HelloQueryDslRepository helloQueryDslRepository;
    @Test
    @DisplayName("Query Factory를 활용한 기본 엔티티 조회")
    void queryTest() {
        HelloQueryDsl hello = new HelloQueryDsl();
        em.persist(hello);

        JPAQueryFactory query = new JPAQueryFactory(em);
        QHelloQueryDsl qHelloQueryDsl = QHelloQueryDsl.helloQueryDsl;

        HelloQueryDsl result = query
                .selectFrom(qHelloQueryDsl)
                .fetchOne();

        assertEquals(result, hello);
        assertEquals(result.getId(), hello.getId());
    }

    @Test
    @DisplayName("JpaRepository 기본 기능 활용한 엔티티 조회")
    void repositoryTest() {
        String name = "test";
        HelloQueryDsl hello = new HelloQueryDsl();
        hello.setName(name);
        em.persist(hello);

        List<HelloQueryDsl> result = helloQueryDslRepository.findByName(name);

        assertEquals(result.get(0).getName(), hello.getName());
    }

    @Test
    @DisplayName("Custom QueryDsl Repository를 활용한 DTO 직접 조회")
    void queryDslRepositoryTest() {
        String name = "test";
        HelloQueryDsl hello = new HelloQueryDsl();
        hello.setName(name);
        em.persist(hello);

        List<HelloQueryDto> result = helloQueryDslRepository.search();

        assertEquals(result.get(0).getName(), hello.getName());
    }


}