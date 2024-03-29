package com.ssafy.chat.dto;

import lombok.Data;

@Data
public class FcmDto {

    private String sender;
    private Long receiverId;
    private String type;
    private String content;

}
