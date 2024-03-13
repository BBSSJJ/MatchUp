package com.ssafy.matchup.auth.service;

import com.ssafy.matchup.auth.dto.JwtTokenRequestDto;
import com.ssafy.matchup.auth.dto.JwtTokenResponseDto;

public interface AuthService {
    JwtTokenResponseDto issueJwtToken(JwtTokenRequestDto jwtTokenRequestDto);

    void reissueRefreshToken();
}
