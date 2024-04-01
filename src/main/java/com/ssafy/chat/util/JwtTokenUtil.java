package com.ssafy.chat.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class JwtTokenUtil {

    @Value("${jwt.secret-key}")
    private String secretKey;

    public Long getUserId(HttpServletRequest request) {

        Cookie[] Cookies = request.getCookies();
        String accessToken = null;

        for (Cookie cookie : Cookies) {
            if (cookie.getName().equals("accessToken")) {
                accessToken = cookie.getValue();
                break;
            }
        }

        if (accessToken == null) return null;

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(accessToken)
                .getBody();

        return Long.parseLong(claims.getId());
    }
}
