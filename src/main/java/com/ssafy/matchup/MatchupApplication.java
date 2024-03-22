package com.ssafy.matchup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MatchupApplication {

    public static void main(String[] args) {
        SpringApplication.run(MatchupApplication.class, args);
    }

}
