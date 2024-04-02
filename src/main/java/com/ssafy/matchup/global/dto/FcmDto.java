package com.ssafy.matchup.global.dto;

import lombok.Data;

@Data
public class FcmDto {

    private String name;
    private String iconUrl;
    private Long receiverId;
    private String type;
    private String content;

}
