package com.ssafy.matchup.user.feedback.service;

import com.ssafy.matchup.user.feedback.dto.FeedbackDto;
import com.ssafy.matchup.user.feedback.dto.FeedbackScoreDto;

import java.util.List;

public interface FeedbackService {
    void writeFeedback(Long userId, FeedbackDto feedbackDto);

    List<FeedbackScoreDto> getFeedbacks(Long userId);
}
