package com.ssafy.chat.controller;

import com.ssafy.chat.dto.ChatDto;
import com.ssafy.chat.dto.ChatRoomDto;
import com.ssafy.chat.dto.RecruitDto;
import com.ssafy.chat.service.ChatService;
import com.ssafy.chat.service.RecruitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private final ChatService chatService;
    private final RecruitService recruitService;

    @MessageMapping("/chat/{roomId}")
    public void sendMessage(@DestinationVariable String roomId, ChatDto chatDto) throws Exception {

        chatService.sendChat(roomId, chatDto);
    }

    public void sendRecuit(RecruitDto recruitDto) throws Exception {

        String method = recruitDto.getMethod();
        recruitDto.setTimestamp(LocalDateTime.now());

        switch (method) {
            case "create":
                recruitService.insertRecruit(recruitDto);
                break;
            case "delete":
                recruitService.deleteRecruit(recruitDto);
                break;
            case "update":
                recruitService.updateRecruit(recruitDto);
                break;
        }
    }

}
