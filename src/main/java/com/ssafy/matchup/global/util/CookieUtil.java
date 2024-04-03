package com.ssafy.matchup.global.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.matchup.user.main.dto.request.UserSnsDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

    public ResponseCookie createUserCookie(UserSnsDto user) throws JsonProcessingException {

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

    public boolean firstRead(HttpServletRequest request, HttpServletResponse response, Long articleId) {
        Cookie[] cookies = request.getCookies();
        Cookie oldCookie = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("visitedArticle")) {
                    oldCookie = cookie;
                }
            }
        }

        if (oldCookie == null) {
            ResponseCookie newCookie = ResponseCookie.from("visitedArticle", articleId.toString())
                    .secure(true)
                    .path("/")
                    .sameSite("None")
                    .maxAge(60 * 60 * 24)
                    .domain(domainUrl)
                    .build();
            response.addHeader("Set-Cookie", newCookie.toString());
            return true;
        } else {
            if (!oldCookie.getValue().contains(articleId.toString())) {
                ResponseCookie newCookie = ResponseCookie.from("visitedArticle", oldCookie.getValue() + "_" + articleId)
                        .secure(true)
                        .path("/")
                        .sameSite("None")
                        .maxAge(60 * 60 * 24)
                        .domain(domainUrl)
                        .build();
                response.addHeader("Set-Cookie", newCookie.toString());
                return true;
            }
            return false;
        }
    }
}
