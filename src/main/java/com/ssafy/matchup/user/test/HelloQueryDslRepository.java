package com.ssafy.matchup.user.test;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HelloQueryDslRepository extends JpaRepository<HelloQueryDsl, Long>, HelloQueryDslRepositoryCustom {
    List<HelloQueryDsl> findByName(String name);
}
