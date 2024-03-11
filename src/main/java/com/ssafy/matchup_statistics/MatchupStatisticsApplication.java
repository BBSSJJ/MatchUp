package com.ssafy.matchup_statistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class MatchupStatisticsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatchupStatisticsApplication.class, args);
	}

}
