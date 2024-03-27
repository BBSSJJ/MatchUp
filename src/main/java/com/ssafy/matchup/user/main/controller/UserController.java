package com.ssafy.matchup.user.main.controller;

import com.ssafy.matchup.user.main.dto.UserDto;
import com.ssafy.matchup.user.main.dto.request.LoginUserRequestDto;
import com.ssafy.matchup.user.main.dto.request.RegistUserRequestDto;
import com.ssafy.matchup.user.main.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping("/regist")
    ResponseEntity<UserDto> userRegist(@RequestBody RegistUserRequestDto registUserRequestDto) {
        log.info("in regist");
        log.info(registUserRequestDto.toString());
        UserDto user = userService.addUser(registUserRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("id", String.valueOf(user.getUserId()))
                .header("role", String.valueOf(user.getRole()))
                .body(user);
    }

    @PostMapping("/login")
    ResponseEntity<UserDto> userLogin(@RequestBody LoginUserRequestDto loginUserRequestDto) {
        log.info("in login");
        log.info(loginUserRequestDto.toString());
        UserDto user = userService.findUser(loginUserRequestDto);

        return ResponseEntity.status(HttpStatus.OK)
                .header("id", String.valueOf(user.getUserId()))
                .header("role", String.valueOf(user.getRole()))
                .body(user);
    }

    @GetMapping("/logout")
    ResponseEntity<Void> userLogout() {
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
