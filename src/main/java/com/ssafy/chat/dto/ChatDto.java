package com.ssafy.chat.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatDto {

    private Long userId;
    private String name;
    private String iconUrl;
    private String content;
    private LocalDateTime timestamp;

}
