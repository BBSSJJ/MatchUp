package com.ssafy.chat.controller;

import com.ssafy.chat.dto.ChatDto;
import com.ssafy.chat.dto.ChatRoomDto;
import com.ssafy.chat.dto.ListDataDto;
import com.ssafy.chat.dto.MessageDataDto;
import com.ssafy.chat.service.ChatService;
import com.ssafy.chat.util.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chats")
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatService chatService;
    private final JwtTokenUtil jwtTokenUtil;

    @GetMapping("/rooms")
    public ResponseEntity<?> showChatRooms(HttpServletRequest request) {

        List<ChatRoomDto> data = chatService.findRooms(jwtTokenUtil.getUserId(request));

        return new ResponseEntity<>(new ListDataDto(data), HttpStatus.OK);
    }

    @GetMapping("/rooms/{roomId}")
    public ResponseEntity<?> showChattings(HttpServletRequest request, @PathVariable String roomId) {

        List<ChatDto> data = chatService.findChattings(jwtTokenUtil.getUserId(request), roomId);

        return new ResponseEntity<>(new ListDataDto(data), HttpStatus.OK);
    }

    @PostMapping("/rooms")
    public ResponseEntity<?> createChatRoom(HttpServletRequest request, @RequestBody ChatRoomDto chatRoomDto) {

        try {
            log.error("input ChatRoomDto : {}", chatRoomDto.toString());
            chatService.createChatRoom(jwtTokenUtil.getUserId(request), chatRoomDto);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageDataDto(e.getMessage()), HttpStatus.OK);
        }

        return new ResponseEntity<>(new MessageDataDto("created"), HttpStatus.OK);
    }

    @DeleteMapping("/rooms/{roomId}")
    public ResponseEntity<?> deleteChatRoom(HttpServletRequest request, @PathVariable String roomId) {

        try {
            chatService.deleteChatRoom(jwtTokenUtil.getUserId(request), roomId);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageDataDto(e.getMessage()), HttpStatus.OK);
        }

        return new ResponseEntity<>(new MessageDataDto("deleted"), HttpStatus.OK);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<?> showChatRoomWithUser(HttpServletRequest request, @PathVariable Long userId) {

        ChatRoomDto chatRoomDto = chatService.findRoom(jwtTokenUtil.getUserId(request), userId);

        if (chatRoomDto == null) {
            return new ResponseEntity<>(new MessageDataDto("not exist chatroom"), HttpStatus.OK);
        }

        return new ResponseEntity<>(chatRoomDto, HttpStatus.OK);
    }

}