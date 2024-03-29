package com.ssafy.matchup.global.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Slf4j
public class WebClientConfig {
    @Bean
    public WebClient webClient() {
        ExchangeStrategies changeBufferSize = ExchangeStrategies.builder()
                .codecs(clientCodecConfigurer -> clientCodecConfigurer.defaultCodecs().maxInMemorySize(-1))
                .build();

        return WebClient.builder()
                .exchangeStrategies(changeBufferSize)
                .defaultHeader("Accept-Charset", "application/x-www-form-urlencoded; charset=UTF-8")
                .build();
    }
}
