package com.ssafy.matchup.auth.service;

import com.ssafy.matchup.auth.dto.JwtToken;

public interface AuthService {
    JwtToken issueJwtToken(String id, String role);

    String reissueAccessToken(String refreshToken);
}
