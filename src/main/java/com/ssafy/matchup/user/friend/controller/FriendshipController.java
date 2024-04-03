package com.ssafy.matchup.user.friend.controller;

import com.ssafy.matchup.global.dto.ListDto;
import com.ssafy.matchup.global.dto.MessageDto;
import com.ssafy.matchup.global.util.JwtTokenUtil;
import com.ssafy.matchup.user.friend.entity.FriendStatus;
import com.ssafy.matchup.user.friend.service.FriendshipService;
import com.ssafy.matchup.user.main.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/friends")
@Slf4j
public class FriendshipController {

    private final JwtTokenUtil jwtTokenUtil;
    private final FriendshipService friendshipService;

    @Operation(summary = "친구 목록 조회", description = "쿼리 파라미터로 FRIEND, SENT, RECEIVED 필수로 넣어서 친구 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<ListDto<UserDto>> showFriendList(HttpServletRequest request, @RequestParam(value = "friendStatus") FriendStatus friendStatus) {
        return new ResponseEntity<>(new ListDto<>(friendshipService.showFriendList(jwtTokenUtil.getUserId(request), friendStatus)), HttpStatus.OK);
    }

    @Operation(summary = "친구 검색", description = "쿼리 파라미터로 KEYWORD를 받아서 친구 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<ListDto<UserDto>> showFriendList(HttpServletRequest request, @RequestParam(value = "keyword") String keyword) {
        return new ResponseEntity<>(new ListDto<>(friendshipService.showUserList(keyword)), HttpStatus.OK);
    }

    @Operation(summary = "친구 요청", description = "친구 요청하는 API 입니다.")
    @PostMapping("/{friend-id}")
    public ResponseEntity<?> requestFriendship(HttpServletRequest request, @PathVariable("friend-id") Long friendId) {
        friendshipService.sendFriendRequest(jwtTokenUtil.getUserId(request), friendId);
        return new ResponseEntity<>(new MessageDto("send friend request success"), HttpStatus.OK);
    }

    @Operation(summary = "친구 요청", description = "친구 수락하는 API 입니다.")
    @PatchMapping("/{friend-id}")
    public ResponseEntity<?> acceptFriendship(HttpServletRequest request, @PathVariable("friend-id") Long friendId) {
        friendshipService.allowFriendRequest(jwtTokenUtil.getUserId(request), friendId);
        return new ResponseEntity<>(new MessageDto("allow friend request"), HttpStatus.OK);

    }

    @Operation(summary = "친구 삭제, 거절", description = "친구를 삭제하거나 요청을 거절하는 API 입니다.")
    @DeleteMapping("/{friend-id}")
    public ResponseEntity<?> deleteFriendship(HttpServletRequest request, @PathVariable("friend-id") Long friendId) {
        friendshipService.deleteFriendship(jwtTokenUtil.getUserId(request), friendId);
        return new ResponseEntity<>(new MessageDto("deleted"), HttpStatus.OK);
    }
}
