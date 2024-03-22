package com.ssafy.matchup.user.main.service;

import com.ssafy.matchup.user.main.dto.request.RegistUserRequestDto;


public interface UserService {

    void addUser(RegistUserRequestDto registUserRequestDto);
}
