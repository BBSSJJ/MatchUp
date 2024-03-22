package com.ssafy.matchup_statistics.global.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@Slf4j
public class RestTemplateConfig {

    @Value("${riot.api-key}")
    private String token;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        log.info("token : {}", token);
        return restTemplateBuilder
                .defaultHeader("Accept-Charset", "application/x-www-form-urlencoded; charset=UTF-8")
                .defaultHeader("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                .defaultHeader("Accept-Encoding", "identity")
                .defaultHeader("X-Riot-Token", token)
                .build();
    }
}
