package com.ssafy.matchup.global.config;

import com.ssafy.matchup.global.dto.MessageDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DuplicateKeyException;
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

    // [400]Entity를 PK로 조회시 찾을 수 없을 때
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<MessageDto> handleEntityNotFoundException(EntityNotFoundException e) {
        return new ResponseEntity<>(new MessageDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    // [400]로그인 시 해당 아이디 없을때
    @ExceptionHandler(UsernameNotFoundException.class)
    protected ResponseEntity<MessageDto> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return new ResponseEntity<>(new MessageDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    // [401]로그인 시 해당 비밀번호가 일치하지 않을 때
    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<MessageDto> handleBadCredentialsException(BadCredentialsException e) {
        return new ResponseEntity<>(new MessageDto(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    // [500]서버 오류
    @ExceptionHandler(InternalError.class)
    protected ResponseEntity<MessageDto> handleInternalServerException(InternalError e) {
        return new ResponseEntity<>(new MessageDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalStateException.class)
    protected ResponseEntity<MessageDto> handleIllegalStateException(IllegalStateException e) {
        return new ResponseEntity<>(new MessageDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    // [403] 수정, 삭제 등 data 주인과 요청자가 일치하지 않을 때
    @ExceptionHandler(SecurityException.class)
    protected ResponseEntity<MessageDto> handleSecurityException(SecurityException e) {
        return new ResponseEntity<>(new MessageDto(e.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<MessageDto> handleIllegalArgumentException(Exception e) {
        return new ResponseEntity<>(new MessageDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    //소환사 닉네임 이미 사용중일 때
    @ExceptionHandler(DuplicateKeyException.class)
    protected ResponseEntity<MessageDto> handleDuplicateKeyException(Exception e) {
        return new ResponseEntity<>(new MessageDto(e.getMessage()), HttpStatus.CONFLICT);
    }

}