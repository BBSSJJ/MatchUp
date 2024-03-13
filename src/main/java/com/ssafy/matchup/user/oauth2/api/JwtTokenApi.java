package com.ssafy.matchup.user.oauth2.api;


import com.ssafy.matchup.user.oauth2.dto.JwtTokenRequestDto;
import com.ssafy.matchup.user.oauth2.dto.JwtTokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class JwtTokenApi {

    @Value("${ip.server.auth}")
    String authServer;

    private final RestTemplate restTemplate;

    public JwtTokenResponseDto issueJwtToken(JwtTokenRequestDto jwtTokenRequestDto) {
        String url = "http://" + authServer + ":9001/api/auth/issue/token";
        return restTemplate.postForObject(url, jwtTokenRequestDto, JwtTokenResponseDto.class);
    }
}
