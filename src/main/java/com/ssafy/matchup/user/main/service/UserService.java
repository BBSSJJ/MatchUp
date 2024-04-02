package com.ssafy.matchup.user.main.service;

import com.ssafy.matchup.user.main.dto.UserDto;
import com.ssafy.matchup.user.main.dto.request.LoginUserRequestDto;
import com.ssafy.matchup.user.main.dto.request.RegistDumpUserRequestDto;
import com.ssafy.matchup.user.main.dto.request.RegistUserRequestDto;
import com.ssafy.matchup.user.main.dto.request.UserSnsDto;
import com.ssafy.matchup.user.main.dto.response.UserInTierResponseDto;
import com.ssafy.matchup.user.main.entity.Setting;

import java.util.List;


public interface UserService {

    UserDto addUser(RegistUserRequestDto registUserRequestDto);

    UserSnsDto findUser(LoginUserRequestDto loginUserRequestDto);

    UserDto getUser(Long userId);

    Setting getSetting(Long userId);

    void updateSetting(Long userId, Setting useMike);

    List<UserInTierResponseDto> getUsersInTier(Long userId, Boolean useMike);

    void registDumpUser(int page, RegistDumpUserRequestDto registDumpUserRequestDto);
}
