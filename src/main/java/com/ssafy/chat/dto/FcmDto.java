package com.ssafy.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FcmDto {

    private String name;
    private String iconUrl;
    private Long receiverId;
    private String type;
    private String content;

}
