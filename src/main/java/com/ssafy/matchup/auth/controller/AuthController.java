package com.ssafy.matchup.auth.controller;

import com.ssafy.matchup.auth.dto.JwtTokenRequestDto;
import com.ssafy.matchup.auth.dto.JwtTokenResponseDto;
import com.ssafy.matchup.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/issue/token")
    ResponseEntity<JwtTokenResponseDto> issueJwtToken(
            @RequestBody JwtTokenRequestDto jwtTokenRequestDto) {
        return new ResponseEntity<>(authService.issueJwtToken(jwtTokenRequestDto), HttpStatus.OK);
    }

    @PostMapping("/reissue/token")
    ResponseEntity<Void> reissueAccessToken(HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
