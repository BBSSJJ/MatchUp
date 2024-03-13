package com.ssafy.matchup.auth.service;

import com.ssafy.matchup.auth.dto.JwtTokenRequestDto;
import com.ssafy.matchup.auth.dto.JwtTokenResponseDto;
import com.ssafy.matchup.auth.dto.type.AuthorityType;
import com.ssafy.matchup.auth.service.sub.JwtTokenRedisService;
import com.ssafy.matchup.auth.service.sub.JwtTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final JwtTokenService jwtTokenService;

    private final JwtTokenRedisService jwtTokenRedisService;

    @Override
    public JwtTokenResponseDto issueJwtToken(JwtTokenRequestDto jwtTokenRequestDto) {
        Long id = jwtTokenRequestDto.getId();
        AuthorityType role = jwtTokenRequestDto.getRole();

        String accessToken = jwtTokenService.generateAccessToken(id, role);
        String refreshToken = jwtTokenService.generateRefreshToken(id, role);

        log.info("AcessToken / RefreshToken : {} / {}", accessToken, refreshToken);

        jwtTokenRedisService.save(id, refreshToken);

        return JwtTokenResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void reissueRefreshToken() {

    }


}
