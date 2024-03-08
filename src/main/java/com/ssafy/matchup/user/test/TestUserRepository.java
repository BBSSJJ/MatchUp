package com.ssafy.matchup.user.test;

import com.ssafy.matchup.user.main.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestUserRepository extends JpaRepository<User, Long> {
}
