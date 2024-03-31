package com.ssafy.chat;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
@Slf4j
public class ChatApplication {

    @PostConstruct
    public void started() {
        // timezone 셋팅
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }

    @Value("${MATCHUP_CHAT_DB}")
    private String matchupChatDb;

    @PostConstruct
    public void postConstruct() {
        log.info("Using MATCHUP_CHAT_DB: {}", matchupChatDb);
    }


    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }

}
