package com.ssafy.matchup.global.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import java.net.HttpCookie;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenUtil {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${url.domain}")
    private String domainUrl;

    @Value("${jwt.period.access-token}")
    private long periodAccessToken;

    @Value("${jwt.period.refresh-token}")
    private long periodRefreshToken;

    public Claims generateClaims(String id, String role) {
        Claims claims = Jwts.claims().setId(id);
        claims.put("role", role);
        return claims;
    }

    public String generateAccessToken(String id, String role) {
        Claims claims = generateClaims(id, role);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + periodAccessToken))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String id, String role) {
        Claims claims = generateClaims(id, role);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + periodRefreshToken))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims getClaimsFromRefreshToken(String refreshToken) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(refreshToken)
                .getBody();
    }

    public Claims validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            Date expiration = claims.getExpiration();
            if (expiration != null && expiration.before(new Date())) {
                log.info("claims expired : {}", expiration);
                return null;
            }

            return claims;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void writeAccessToken(ServerHttpResponse response, String accessToken) {
        ResponseCookie cookie = ResponseCookie.from("accessToken", accessToken)
                .path("/")
                .domain(domainUrl)
                .sameSite("None")
                .httpOnly(true)
                .secure(true)
                .maxAge(periodAccessToken)
                .build();
        response.addCookie(cookie);
    }

    public void writeRefreshToken(ServerHttpResponse response, String refreshToken) {
        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .path("/")
                .domain(domainUrl)
                .sameSite("None")
                .httpOnly(true)
                .secure(true)
                .maxAge(periodRefreshToken)
                .build();

        response.addCookie(cookie);
    }

    public void deleteAccessToken(ServerHttpRequest request) {
        HttpCookie cookie = new HttpCookie("accessToken", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        request.getHeaders().add("Set-Cookie", cookie.toString());
    }

    public void deleteRefreshToken(ServerHttpRequest request) {
        HttpCookie cookie = new HttpCookie("refreshToken", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        request.getHeaders().add("Set-Cookie", cookie.toString());
    }
}
