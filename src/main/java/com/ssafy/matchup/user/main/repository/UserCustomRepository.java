package com.ssafy.matchup.user.main.repository;

import com.ssafy.matchup.user.main.dto.UserDto;
import com.ssafy.matchup.user.main.entity.User;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface UserCustomRepository {
    List<User> findByTierAndLeagueRankAndUseMike(List<Pair<String, String>> tierLeagueRankList, Boolean useMike);

    List<UserDto> findUserByKeyword(String keyword);
}
