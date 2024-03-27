package com.ssafy.chat.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RecruitDto {

    private String method;
    private String objectId;
    private Long userId;
    private String name;
    private String iconUrl;
    private String tier;
    private String line;
    private String wishLine;
    private String gameType;
    private String content;
    private Long win;
    private Long lose;
    private Double kill;
    private Double death;
    private Double assist;
    private LocalDateTime timestamp;

}
