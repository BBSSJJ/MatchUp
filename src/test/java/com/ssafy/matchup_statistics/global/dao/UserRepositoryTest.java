package com.ssafy.matchup_statistics.global.dao;

import com.ssafy.matchup_statistics.global.dto.jpa.UserRiotAccountDto;
import com.ssafy.matchup_statistics.global.entity.RiotAccount;
import com.ssafy.matchup_statistics.global.entity.SummonerProfile;
import com.ssafy.matchup_statistics.global.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class UserRepositoryTest {

    @PersistenceContext
    EntityManager entityManager;

    UserRepository userRepository;

    @BeforeEach
    @Transactional
    void init() {
        userRepository = new UserRepository(entityManager);

        User user = User.builder()
                .build();

        RiotAccount riotAccount = RiotAccount.builder()
                .id("test id")
                .user(user)
                .summonerProfile(SummonerProfile.builder()
                        .name("test name")
                        .tag("kr1")
                        .build())
                .build();

        entityManager.persist(user);
        entityManager.persist(riotAccount);
    }

    @Test
    @DisplayName("유저 정보와 라이엇 계정 한번에 불러오는지 테스트")
    void userRiotAccountFindTest() {
        // given
        long id = 1;

        // when
        UserRiotAccountDto userRiotAccountDto = userRepository.getUserRiotAccountDto(1);

        // then
        assertThat(userRiotAccountDto.getUserInfo().getId()).isEqualTo(1);
        assertThat(userRiotAccountDto.getRiotAccountInfo().getSummonerProfile().getName()).isEqualTo("test name");
        assertThat(userRiotAccountDto.getRiotAccountInfo().getSummonerProfile().getTag()).isEqualTo("kr1");
    }
}