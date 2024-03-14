package com.ssafy.chat.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class ChatRoomDto {

    private String roomId;
    private ArrayList<Long> participants;

}
