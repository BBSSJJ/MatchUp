package com.ssafy.matchup.auth.service;

import com.ssafy.matchup.auth.dto.JwtToken;
import com.ssafy.matchup.auth.service.sub.JwtTokenRedisService;
import com.ssafy.matchup.global.util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final JwtTokenUtil jwtTokenUtil;

    private final JwtTokenRedisService jwtTokenRedisService;

    @Override
    public JwtToken issueJwtToken(String id, String role) {

        String accessToken = jwtTokenUtil.generateAccessToken(id, role);
        String refreshToken = jwtTokenUtil.generateRefreshToken(id, role);

        log.info("issue AccessToken : {}", accessToken);
        log.info("issue RefreshToken : {}", refreshToken);

        jwtTokenRedisService.save(id, refreshToken);

        return JwtToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public String reissueAccessToken(String refreshToken) {
        Claims claims = jwtTokenUtil.getClaimsFromRefreshToken(refreshToken);
        if (claims.getId().isEmpty()) {
            return null;
        }
        String id = claims.getId();
        String role = (String) claims.get("role");

        String refreshTokenRedis = jwtTokenRedisService.findById(id);
        if (!refreshToken.equals(refreshTokenRedis)) {
            return null;
        }
        return jwtTokenUtil.generateAccessToken(id, role);
    }

    @Override
    public void deleteRefreshToken(Claims claims) {
        String id = claims.getId();
        jwtTokenRedisService.deleteById(id);
    }

}
