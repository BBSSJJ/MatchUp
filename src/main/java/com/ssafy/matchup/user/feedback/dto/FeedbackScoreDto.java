package com.ssafy.matchup.user.feedback.dto;

import com.ssafy.matchup.user.feedback.entity.Feedback;
import lombok.Builder;
import lombok.Data;

@Data
public class FeedbackScoreDto {
    private Long feedbackedUserId;
    private Integer score;

    @Builder
    public FeedbackScoreDto(Feedback feedback) {
        this.feedbackedUserId = feedback.getFeedbackedUser().getId();
        this.score = feedback.getScore();
    }
}
