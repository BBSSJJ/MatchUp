package com.ssafy.chat.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatDto {

    private String sender;
    private String content;
    private LocalDateTime timestamp;

}
