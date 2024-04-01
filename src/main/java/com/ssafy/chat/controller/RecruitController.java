package com.ssafy.chat.controller;

import com.ssafy.chat.dto.ListDataDto;
import com.ssafy.chat.dto.MessageDataDto;
import com.ssafy.chat.dto.RecruitDto;
import com.ssafy.chat.service.RecruitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recruits")
@RequiredArgsConstructor
@Tag(name = "Recruit", description = "구인게시판 관련 기능입니다.")
public class RecruitController {

    private final RecruitService recruitService;

    @Operation(summary = "구인글 목록 조회", description = "구인글 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "구인글 목록 리스트", // 응답코드 200일때 응답 설명
                    content = @Content(schema = @Schema(implementation = RecruitDto.class))), // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
    })
    @GetMapping
    public ResponseEntity<?> showRecruits() {

        List<RecruitDto> list = recruitService.findRecruits();

        return new ResponseEntity<>(new ListDataDto(list), HttpStatus.OK);
    }
}
