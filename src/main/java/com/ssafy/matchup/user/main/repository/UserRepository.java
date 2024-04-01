package com.ssafy.matchup.user.main.repository;

import com.ssafy.matchup.user.main.entity.User;
import com.ssafy.matchup.user.main.entity.type.SnsType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserCustomRepository {

    @Query("SELECT u FROM users u " +
            "JOIN FETCH u.riotAccount ra " +
            "JOIN FETCH ra.summonerProfile " +
            "WHERE u.snsType = :snsType AND u.snsId = :snsId")
    Optional<User> findUserBySnsTypeAndSnsId(SnsType snsType, String snsId);

    @Query("SELECT u FROM users u " +
            "JOIN FETCH u.riotAccount ra " +
            "JOIN FETCH ra.summonerProfile " +
            "JOIN FETCH u.setting " +
            "WHERE u.id = :id")
    Optional<User> findUserById(Long id);

    @Query("SELECT u FROM users u " +
            "JOIN FETCH u.setting " +
            "WHERE u.id = :id")
    Optional<User> findSettingById(Long id);
}
