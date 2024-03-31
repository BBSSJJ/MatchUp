package com.ssafy.matchup.user.feedback.service;

import com.ssafy.matchup.user.feedback.dto.FeedbackDto;

public interface FeedbackService {
    void writeFeedback(Long userId, FeedbackDto feedbackDto);
}
