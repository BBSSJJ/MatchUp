package com.ssafy.matchup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication (exclude = SecurityAutoConfiguration.class)
public class MatchupApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatchupApplication.class, args);
	}

}
