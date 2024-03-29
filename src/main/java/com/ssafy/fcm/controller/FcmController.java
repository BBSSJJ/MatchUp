package com.ssafy.fcm.controller;

import com.ssafy.fcm.dto.MessageDataDto;
import com.ssafy.fcm.dto.ClientTokenDto;
import com.ssafy.fcm.service.FcmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fcm")
@RequiredArgsConstructor
@Slf4j
public class FcmController {

    private final FcmService fcmService;

    @PostMapping("/clients/{userId}")
    public ResponseEntity<?> registClientToken(@PathVariable Long userId, @RequestBody ClientTokenDto clientTokenDto) {

        fcmService.registClientToken(userId, clientTokenDto.getToken());

        return new ResponseEntity<>(new MessageDataDto("regist client Token"), HttpStatus.OK);
    }

//    @PostMapping("/send")
//    public ResponseEntity<?> sendMessage(@RequestBody FcmRequestDto fcmRequestDto) {
//
//        String clientToken = fcmService.getClientToken(fcmRequestDto.getReceiver());
//        try{
//            fcmService.sendPersonalMessage(clientToken, fcmRequestDto);
//        } catch (Exception e) {
//            return new ResponseEntity<>(new MessageDataDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        return new ResponseEntity<>(new MessageDataDto("send"), HttpStatus.OK);
//    }
}
