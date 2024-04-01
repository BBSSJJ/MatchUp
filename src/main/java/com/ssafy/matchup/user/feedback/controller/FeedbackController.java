package com.ssafy.matchup.user.feedback.controller;

import com.ssafy.matchup.global.dto.ListDto;
import com.ssafy.matchup.global.util.JwtTokenUtil;
import com.ssafy.matchup.user.feedback.dto.FeedbackDto;
import com.ssafy.matchup.user.feedback.dto.FeedbackScoreDto;
import com.ssafy.matchup.user.feedback.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final JwtTokenUtil jwtTokenUtil;

    @Operation(summary = "피드백 등록", description = "피드백을 등록하는 API입니다. 상대 id, 점수(1~5) Integer형, 코멘트를 body로 받습니다.")
    @PostMapping()
    ResponseEntity<Void> feedbackRegist(HttpServletRequest request, @RequestBody FeedbackDto feedbackDto) {
        feedbackService.writeFeedback(jwtTokenUtil.getUserId(request), feedbackDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/users/{user-id}")
    ResponseEntity<ListDto<FeedbackScoreDto>> feedbackList(@PathVariable("user-id") Long userId) {
        List<FeedbackScoreDto> list = feedbackService.getFeedbacks(userId);
        return new ResponseEntity<>(new ListDto<>(list), HttpStatus.OK);
    }

}
