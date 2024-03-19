package com.ssafy.matchup.user.main.controller;

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

    UserService userService;

    @PostMapping("/regist")
    ResponseEntity<Void> registUser(@RequestBody RegistUserRequestDto registUserRequestDto) {
        userService.registUser(registUserRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
