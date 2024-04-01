package com.ssafy.chat.controller;

import com.ssafy.chat.dto.ChatDto;
import com.ssafy.chat.dto.ChatRoomDto;
import com.ssafy.chat.dto.ListDataDto;
import com.ssafy.chat.dto.MessageDataDto;
import com.ssafy.chat.service.ChatService;
import com.ssafy.chat.util.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Chatroom", description = "채팅방 관련 기능입니다.")
public class ChatController {

    private final ChatService chatService;
    private final JwtTokenUtil jwtTokenUtil;

    @Operation(summary = "채팅방 목록 조회", description = "본인 userId가 포함된 채팅방 정보를 리스트로 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "채팅방 리스트", // 응답코드 200일때 응답 설명
                    content = @Content(schema = @Schema(implementation = ChatRoomDto.class))), // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
    })
    @GetMapping("/rooms")
    public ResponseEntity<?> showChatRooms(HttpServletRequest request) {

        List<ChatRoomDto> data = chatService.findRooms(jwtTokenUtil.getUserId(request));

        return new ResponseEntity<>(new ListDataDto(data), HttpStatus.OK);
    }

    @Operation(summary = "채팅방 정보 조회", description = "상대 userId와 본인 userId가 포함된 채팅방의 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "채팅방 정보", // 응답코드 200일때 응답 설명
                    content = @Content(schema = @Schema(implementation = ChatRoomDto.class))), // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
    })
    @GetMapping("/users/{userId}")
    public ResponseEntity<?> showChatRoomWithUser(HttpServletRequest request, @PathVariable Long userId) {

        ChatRoomDto chatRoomDto = chatService.findRoom(jwtTokenUtil.getUserId(request), userId);

        if (chatRoomDto == null) {
            return new ResponseEntity<>(new MessageDataDto("not exist chatroom"), HttpStatus.OK);
        }

        return new ResponseEntity<>(chatRoomDto, HttpStatus.OK);
    }

    @Operation(summary = "채팅내역 조회", description = "채팅방 roomId로 해당 채팅방의 채팅 내역을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "채팅 리스트", // 응답코드 200일때 응답 설명
                    content = @Content(schema = @Schema(implementation = ChatDto.class))), // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
    })
    @GetMapping("/rooms/{roomId}")
    public ResponseEntity<?> showChattings(HttpServletRequest request, @PathVariable String roomId) {

        List<ChatDto> data = chatService.findChattings(jwtTokenUtil.getUserId(request), roomId);

        return new ResponseEntity<>(new ListDataDto(data), HttpStatus.OK);
    }

    @Operation(summary = "채팅방 생성", description = "본인 userId와 상대 userId를 전달하여 채팅방을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "생성 완료 메세지", // 응답코드 200일때 응답 설명
                    content = @Content(schema = @Schema(implementation = MessageDataDto.class))), // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
            @ApiResponse(responseCode = "500", description = "잘못된 요청(illegal request)", // 응답코드 400일때 응답 설명
                    content = @Content(schema = @Schema(implementation = MessageDataDto.class))) // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
    })
    @PostMapping("/rooms")
    public ResponseEntity<?> createChatRoom(HttpServletRequest request, @RequestBody ChatRoomDto chatRoomDto) {

        try {
            log.error("input ChatRoomDto : {}", chatRoomDto.toString());
            chatService.createChatRoom(jwtTokenUtil.getUserId(request), chatRoomDto);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageDataDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(new MessageDataDto("created"), HttpStatus.OK);
    }

    @Operation(summary = "채팅방 삭제", description = "roomId에 해당하는 채팅방을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 완료 메세지", // 응답코드 200일때 응답 설명
                    content = @Content(schema = @Schema(implementation = MessageDataDto.class))), // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
            @ApiResponse(responseCode = "500", description = "잘못된 요청(illegal request)", // 응답코드 400일때 응답 설명
                    content = @Content(schema = @Schema(implementation = MessageDataDto.class))) // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
    })
    @DeleteMapping("/rooms/{roomId}")
    public ResponseEntity<?> deleteChatRoom(HttpServletRequest request, @PathVariable String roomId) {

        try {
            chatService.deleteChatRoom(jwtTokenUtil.getUserId(request), roomId);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageDataDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(new MessageDataDto("deleted"), HttpStatus.OK);
    }



}