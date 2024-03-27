package com.ssafy.matchup.global.filter;

import com.ssafy.matchup.auth.dto.JwtToken;
import com.ssafy.matchup.auth.service.AuthService;
import com.ssafy.matchup.global.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoginFilter extends AbstractGatewayFilterFactory<LoginFilter.Config> {

    private final JwtTokenUtil jwtTokenUtil;

    private final AuthService authService;

    @Autowired
    public LoginFilter(JwtTokenUtil jwtTokenUtil, AuthService authService) {
        super(Config.class);
        this.jwtTokenUtil = jwtTokenUtil;
        this.authService = authService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            ServerHttpResponse response = exchange.getResponse();
            ServerHttpRequest request = exchange.getRequest();

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                if (response.getStatusCode() == HttpStatus.OK
                        || response.getStatusCode() == HttpStatus.CREATED) {
                    String id = response.getHeaders().getFirst("id");
                    String role = response.getHeaders().getFirst("role");
                    response.getHeaders().remove("id").remove("role");

                    JwtToken jwtToken = authService.issueJwtToken(id, role);

                    jwtTokenUtil.writeAccessToken(response, jwtToken.getAccessToken());
                    jwtTokenUtil.writeRefreshToken(response, jwtToken.getRefreshToken());

                    log.info("login success : {}, {}", id, role);
                    log.info("cookies after login in filter : {}", response.getCookies());
                }
            }));
        };
    }

    public static class Config {

    }

}
