package com.ssafy.matchup_statistics.global.dao;

import com.ssafy.matchup_statistics.global.dto.jpa.UserRiotAccountDto;

public interface UserDao {

    UserRiotAccountDto getUserRiotAccountDto(long userId);
}
