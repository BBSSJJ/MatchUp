package com.ssafy.matchup.user.feedback.repository;

import com.ssafy.matchup.user.feedback.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
