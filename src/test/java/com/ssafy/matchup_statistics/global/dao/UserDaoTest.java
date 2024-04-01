package com.ssafy.matchup_statistics.global.dao;

import com.ssafy.matchup_statistics.global.entity.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Slf4j
class UserDaoTest {

    @Autowired
    TestEntityManager em;

    @BeforeEach
    @Transactional
    void init() {
        User user = User.builder().build();
        em.persist(user);
        em.flush();
    }

    @Test
    void jpaTest() {
        User user = em.find(User.class, 1);
        log.info("user : {}", user.getId());
    }
}