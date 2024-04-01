package com.ssafy.matchup_statistics.global.config;

import com.ssafy.matchup_statistics.global.dto.response.MessageDto;
import com.ssafy.matchup_statistics.global.exception.RiotApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    // [400]잘못된 요청일때(validate 시 null이거나, 빈칸 등)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<MessageDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(new MessageDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    // [404]최근 게임 없을때 오류
    @ExceptionHandler(RiotApiException.class)
    protected ResponseEntity<MessageDto> handleNoRentGameException(RiotApiException e) {
        return new ResponseEntity<>(new MessageDto(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    // [500]서버 오류
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<MessageDto> handleInternalServerException(Exception e) {
        return new ResponseEntity<>(new MessageDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}