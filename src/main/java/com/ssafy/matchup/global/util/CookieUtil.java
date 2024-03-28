package com.ssafy.matchup.global.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.matchup.user.main.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class CookieUtil {

    @Value("${url.domain}")
    private String domainUrl;

    @Value("${cookie.period.refresh-token}")
    private int periodRefreshTokenCookie;

    public ResponseCookie createUserCookie(UserDto user) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);
        return ResponseCookie.from("user", URLEncoder.encode(userJson, StandardCharsets.UTF_8))
                .secure(true)
                .path("/")
                .sameSite("None")
                .maxAge(periodRefreshTokenCookie)
                .domain(domainUrl)
                .build();
    }

    public ResponseCookie removeUserCookie() {

        return ResponseCookie.from("user", "")
                .secure(true)
                .path("/")
                .sameSite("None")
                .maxAge(0)
                .domain(domainUrl)
                .build();
    }
}
