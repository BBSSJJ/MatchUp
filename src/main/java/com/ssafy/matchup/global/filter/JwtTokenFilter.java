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
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class JwtTokenFilter extends AbstractGatewayFilterFactory<JwtTokenFilter.Config> {

    private final JwtTokenUtil jwtTokenUtil;

    private final AuthService authService;

    @Autowired
    public JwtTokenFilter(JwtTokenUtil jwtTokenUtil, AuthService authService) {
        super(Config.class);
        this.jwtTokenUtil = jwtTokenUtil;
        this.authService = authService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            List<HttpCookie> refreshTokenCookies = request.getCookies().getOrDefault("refreshToken", Collections.emptyList());
            List<HttpCookie> accessTokenCookies = request.getCookies().getOrDefault("accessToken", Collections.emptyList());

            if (refreshTokenCookies.size() != 1
                    || jwtTokenUtil.validateToken(refreshTokenCookies.get(0).getValue()) == null) {
                return handleUnAuthorized(exchange);
            }

            if (accessTokenCookies.size() != 1
                    || jwtTokenUtil.validateToken(accessTokenCookies.get(0).getValue()) == null) {
                String accessToken = authService.reissueAccessToken(refreshTokenCookies.get(0).getValue());

                if (accessToken == null) {
                    return handleUnAuthorized(exchange);
                }
                jwtTokenUtil.writeAccessToken(response, accessToken);
            }
            return chain.filter(exchange);
        };
    }

    public static class Config {

    }

    private Mono<Void> handleUnAuthorized(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();

        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }
}
