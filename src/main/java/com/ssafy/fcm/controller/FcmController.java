package com.ssafy.fcm.controller;

import com.ssafy.fcm.dto.MessageDataDto;
import com.ssafy.fcm.dto.ClientTokenDto;
import com.ssafy.fcm.service.FcmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Alarm Controller", description = "클라이언트 토큰을 등록하기 위한 API 입니다.") // 문서의 해당 컨트롤러(탭)을 구분하는 태그 생성
@RestController
@RequestMapping("/api/alarm")
@RequiredArgsConstructor
@Slf4j
public class FcmController {

    private final FcmService fcmService;

    @Operation(summary = "클라이언트 토큰 등록", description = "클라이언트 토큰을 등록하는 API입니다.") // 해당 API가 어떤 역할을 하는지 설명
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "클라이언트 토큰 등록 성공", // 응답코드 200일때 응답 설명
                    content = @Content(schema = @Schema(implementation = MessageDataDto.class))), // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
    })
    @PostMapping("/clients/{userId}")
    public ResponseEntity<?> registClientToken(@PathVariable Long userId, @RequestBody ClientTokenDto clientTokenDto) {

        fcmService.registClientToken(userId, clientTokenDto.getToken());

        return new ResponseEntity<>(new MessageDataDto("regist client Token"), HttpStatus.OK);
    }

}
