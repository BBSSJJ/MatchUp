package com.ssafy.matchup.user.feedback.entity;

import com.ssafy.matchup.user.main.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "feedback")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "score", nullable = false)
    private Integer score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feedbacked_user_id")
    private User feedbackedUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feedbacking_user_id")
    private User feedbackingUser;
}
