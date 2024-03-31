package com.ssafy.matchup.user.feedback.controller;

import com.ssafy.matchup.global.util.JwtTokenUtil;
import com.ssafy.matchup.user.feedback.dto.FeedbackDto;
import com.ssafy.matchup.user.feedback.service.FeedbackService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping()
    ResponseEntity<Void> feedbackRegist(HttpServletRequest request, @RequestBody FeedbackDto feedbackDto) {
        feedbackService.writeFeedback(jwtTokenUtil.getUserId(request), feedbackDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
