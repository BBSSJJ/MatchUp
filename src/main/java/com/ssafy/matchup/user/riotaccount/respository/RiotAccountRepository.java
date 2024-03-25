package com.ssafy.matchup.user.riotaccount.respository;

import com.ssafy.matchup.user.riotaccount.entity.RiotAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RiotAccountRepository extends JpaRepository<RiotAccount, String> {
    Optional<RiotAccount> findRiotAccountBySummonerProfile_NameAndSummonerProfile_Tag(String name, String tag);
}
