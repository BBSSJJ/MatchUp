package com.ssafy.matchup.user.friend.controller;

import com.ssafy.matchup.global.dto.ListDto;
import com.ssafy.matchup.global.dto.MessageDto;
import com.ssafy.matchup.global.util.JwtTokenUtil;
import com.ssafy.matchup.user.friend.dto.FriendRequestDto;
import com.ssafy.matchup.user.friend.service.FriendshipService;
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

    @GetMapping
    public ResponseEntity<?> showFriendList(HttpServletRequest request) {

        return new ResponseEntity<>(new ListDto<>(friendshipService.showFriendsList(jwtTokenUtil.getUserId(request))), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> registFriendship(HttpServletRequest request, @RequestBody FriendRequestDto friendRequestDto) {

        friendshipService.registFriendship(jwtTokenUtil.getUserId(request), friendRequestDto.getFriendId());

        return new ResponseEntity<>(new MessageDto("regist"), HttpStatus.OK);
    }

    @DeleteMapping("/{friendId}")
    public ResponseEntity<?> deleteFriendship(HttpServletRequest request, @PathVariable Long friendId) {

        friendshipService.deleteFriendship(jwtTokenUtil.getUserId(request), friendId);

        return new ResponseEntity<>(new MessageDto("deleted"), HttpStatus.OK);
    }
}
