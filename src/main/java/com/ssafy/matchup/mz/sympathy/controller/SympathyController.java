package com.ssafy.matchup.mz.sympathy.controller;

import com.ssafy.matchup.global.util.JwtTokenUtil;
import com.ssafy.matchup.mz.sympathy.dto.request.UpdateSympathyRequestDto;
import com.ssafy.matchup.mz.sympathy.service.SympathyService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mz/sympathies")
@RequiredArgsConstructor
@Slf4j
public class SympathyController {
    private final SympathyService sympathyService;
    private final JwtTokenUtil jwtTokenUtil;

    @Operation(summary = "공감 수정", description = "공감을 수정하는 API입니다. 누른적 없을 때는 생성, 눌렀던 것을 눌렀을 때는 삭제, 눌렀던 것과 다른 것을 눌렀을 때는 수정")
    @PatchMapping("/{article-id}")
    ResponseEntity<Void> sympathyUpdate(HttpServletRequest request, @PathVariable("article-id") Long articleId, @RequestBody UpdateSympathyRequestDto updateSympathyRequestDto) {
        log.info("in sympathy update : {}", updateSympathyRequestDto);
        sympathyService.updateSympathy(jwtTokenUtil.getUserId(request), articleId, updateSympathyRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}