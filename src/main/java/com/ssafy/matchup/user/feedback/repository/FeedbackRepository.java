package com.ssafy.matchup.user.feedback.repository;

import com.ssafy.matchup.user.feedback.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    //TODO : 쿼리 두 번 나가는지 확인
    @Query("SELECT f FROM feedback f " +
            "WHERE f.feedbackingUser.id = :userId")
    List<Feedback> findFeedbacksByFeedbackingUser_Id(Long userId);
}
