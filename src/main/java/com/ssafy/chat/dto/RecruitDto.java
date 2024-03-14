package com.ssafy.chat.dto;

import lombok.Data;

@Data
public class RecruitDto {

    private String method;
    private String objectId;
    private String name;
    private String url;
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

}
