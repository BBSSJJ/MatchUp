package com.ssafy.matchup.user.main.service;

import com.ssafy.matchup.user.main.dto.UserDto;
import com.ssafy.matchup.user.main.dto.request.LoginUserRequestDto;
import com.ssafy.matchup.user.main.dto.request.RegistDumpUserRequestDto;
import com.ssafy.matchup.user.main.dto.request.RegistUserRequestDto;
import com.ssafy.matchup.user.main.entity.Setting;

import java.util.List;


public interface UserService {

    UserDto addUser(RegistUserRequestDto registUserRequestDto);

    UserDto findUser(LoginUserRequestDto loginUserRequestDto);

    UserDto getUser(Long userId);
    Setting getSetting(Long userId);
    void updateSetting(Long userId, Setting useMike);
    List<String> getUsersInTier(Long userId);
    void registDumpUser(int page, RegistDumpUserRequestDto registDumpUserRequestDto);
}
