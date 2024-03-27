package com.ssafy.matchup.user.main.service;

import com.ssafy.matchup.user.main.dto.UserDto;
import com.ssafy.matchup.user.main.dto.request.LoginUserRequestDto;
import com.ssafy.matchup.user.main.dto.request.RegistUserRequestDto;


public interface UserService {

    UserDto addUser(RegistUserRequestDto registUserRequestDto);

    UserDto findUser(LoginUserRequestDto loginUserRequestDto);
}
