package com.ssafy.matchup.user.main.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.matchup.global.util.CookieUtil;
import com.ssafy.matchup.user.main.dto.UserDto;
import com.ssafy.matchup.user.main.dto.request.LoginUserRequestDto;
import com.ssafy.matchup.user.main.dto.request.RegistDumpUserRequestDto;
import com.ssafy.matchup.user.main.dto.request.RegistUserRequestDto;
import com.ssafy.matchup.user.main.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final CookieUtil cookieUtil;
    private final UserService userService;

    @PostMapping("/regist")
    ResponseEntity<UserDto> userRegist(@RequestBody RegistUserRequestDto registUserRequestDto) throws JsonProcessingException {
        UserDto user = userService.addUser(registUserRequestDto);

        ResponseCookie cookie = cookieUtil.createUserCookie(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("id", String.valueOf(user.getUserId()))
                .header("role", String.valueOf(user.getRole()))
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(user);
    }

    @PostMapping("/login")
    ResponseEntity<UserDto> userLogin(@RequestBody LoginUserRequestDto loginUserRequestDto) throws JsonProcessingException {
        UserDto user = userService.findUser(loginUserRequestDto);

        ResponseCookie cookie = cookieUtil.createUserCookie(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("id", String.valueOf(user.getUserId()))
                .header("role", String.valueOf(user.getRole()))
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(user);
    }

    @GetMapping("/{user-id}")
    ResponseEntity<UserDto> userInfo(@PathVariable("user-id") Long userId) {
        return new ResponseEntity<>(userService.getUser(userId), HttpStatus.OK);
    }

    @GetMapping("/logout")
    ResponseEntity<Void> userLogout() throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, String.valueOf(cookieUtil.removeUserCookie()))
                .body(null);
    }

    @PostMapping("/dump/{page}")
    ResponseEntity<Void> userDumpRegist(@PathVariable("page") int page, @RequestBody RegistDumpUserRequestDto registDumpUserRequestDto) {

        userService.registDumpUser(page, registDumpUserRequestDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
