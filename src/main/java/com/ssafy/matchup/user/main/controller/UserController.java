package com.ssafy.matchup.user.main.controller;

import com.ssafy.matchup.user.main.dto.UserDto;
import com.ssafy.matchup.user.main.dto.request.RegistUserRequestDto;
import com.ssafy.matchup.user.main.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/regist")
    ResponseEntity<UserDto> userRegist(@RequestBody RegistUserRequestDto registUserRequestDto) {
        UserDto user = userService.addUser(registUserRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("id", String.valueOf(user.getUserId()))
                .header("role", String.valueOf(user.getRole()))
                .body(user);
    }


}
