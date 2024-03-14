package com.ssafy.chat.controller;

import com.ssafy.chat.dto.ChatDto;
import com.ssafy.chat.dto.ChatRoomDto;
import com.ssafy.chat.dto.ListDataDto;
import com.ssafy.chat.dto.MessageDataDto;
import com.ssafy.chat.service.ChatService;
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

    @GetMapping("/users/{userId}/rooms")
    public ResponseEntity<?> showChatRooms(@PathVariable Long userId) {

        List<ChatRoomDto> data = chatService.findRooms(userId);

        return new ResponseEntity<>(new ListDataDto(data), HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/rooms/{roomId}")
    public ResponseEntity<?> showChattings(@PathVariable String roomId) {

        List<ChatDto> data = chatService.findChattings(roomId);

        return new ResponseEntity<>(new ListDataDto(data), HttpStatus.OK);
    }

    @PostMapping("/users/{userId}/rooms")
    public ResponseEntity<?> createChatRoom(@RequestBody ChatRoomDto chatRoomDto) {

        chatService.createChatRoom(chatRoomDto);

        return new ResponseEntity<>(new MessageDataDto("created"), HttpStatus.OK);
    }

    @DeleteMapping("/users/{userId}/rooms/{roomId}")
    public ResponseEntity<?> deleteChatRoom(@PathVariable String roomId) {

        chatService.deleteChatRoom(roomId);

        return new ResponseEntity<>(new MessageDataDto("deleted"), HttpStatus.OK);
    }

}
