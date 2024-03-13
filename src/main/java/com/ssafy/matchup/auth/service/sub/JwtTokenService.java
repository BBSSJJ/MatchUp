package com.ssafy.matchup.auth.service.sub;

import com.ssafy.matchup.auth.dto.type.AuthorityType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenService {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.period.access-token}")
    private long periodAccessToken;

    @Value("${jwt.period.refresh-token}")
    private long periodRefreshToken;

    public Claims generateClaims(Long id, AuthorityType role) {
        Claims claims = Jwts.claims().setSubject(Long.toString(id));
        claims.put("role", role);
        return claims;
    }

    public String generateAccessToken(Long id, AuthorityType role) {
        Claims claims = generateClaims(id, role);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + periodAccessToken))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(Long id, AuthorityType role) {
        Claims claims = generateClaims(id, role);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + periodRefreshToken))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();
    }
}
