package com.ssafy.matchup.user.feedback.dto;

import com.ssafy.matchup.user.main.entity.User;
import lombok.Data;

@Data
public class FeedbackDto {
    private String content;
    private Integer score;
    private Long feedbackedUserId;
}
