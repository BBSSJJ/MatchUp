package com.ssafy.matchup.global.filter;

import com.ssafy.matchup.auth.service.AuthService;
import com.ssafy.matchup.global.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class LogoutFilter extends AbstractGatewayFilterFactory<LogoutFilter.Config> {

    private final JwtTokenUtil jwtTokenUtil;

    private final AuthService authService;

    @Autowired
    public LogoutFilter(JwtTokenUtil jwtTokenUtil, AuthService authService) {
        super(LogoutFilter.Config.class);
        this.jwtTokenUtil = jwtTokenUtil;
        this.authService = authService;
    }

    @Override
    public GatewayFilter apply(LogoutFilter.Config config) {
        return (exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            List<HttpCookie> refreshTokenCookies = request.getCookies().getOrDefault("refreshToken", Collections.emptyList());

            if (refreshTokenCookies.size() != 1) {
                authService.deleteRefreshToken(jwtTokenUtil.getClaimsFromRefreshToken(refreshTokenCookies.get(0).getValue()));
            }

            jwtTokenUtil.deleteAccessToken(request);
            jwtTokenUtil.deleteRefreshToken(request);

            response.setStatusCode(HttpStatus.OK);
            return response.setComplete();
        };
    }

    public static class Config {

    }
}
