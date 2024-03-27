package com.ssafy.matchup.auth.service;

import com.ssafy.matchup.auth.dto.JwtToken;
import io.jsonwebtoken.Claims;

public interface AuthService {
    JwtToken issueJwtToken(String id, String role);

    String reissueAccessToken(String refreshToken);

    void deleteRefreshToken(Claims claims);
}
