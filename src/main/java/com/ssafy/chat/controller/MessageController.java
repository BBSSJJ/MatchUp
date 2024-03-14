package com.ssafy.chat.controller;

import com.ssafy.chat.dto.ChatDto;
import com.ssafy.chat.dto.RecruitDto;
import com.ssafy.chat.service.ChatService;
import com.ssafy.chat.service.RecruitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private final ChatService chatService;
    private final RecruitService recruitService;

    @MessageMapping("/chat/{roomId}")
    public void sendMessage(@DestinationVariable String roomId, ChatDto chatDto) throws Exception {
        log.error("채팅룸 번호 : {}", roomId);
        log.error("send message : {}", chatDto.getContent());

        chatService.insertChat(roomId, chatDto);
    }

    @MessageMapping("/recruit")
    public void sendRecuit(RecruitDto recruitDto) throws Exception {

        String method = recruitDto.getMethod();
        log.error("메소드 : {}", method);
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
