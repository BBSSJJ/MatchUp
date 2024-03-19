package com.ssafy.matchup.user.main.service;

import com.ssafy.matchup.user.main.dto.request.RegistUserRequestDto;
import org.springframework.http.HttpStatus;

public interface UserService {
    HttpStatus registUser(RegistUserRequestDto registUserRequestDto);
}
