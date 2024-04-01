package com.ssafy.matchup.user.feedback.service;

import com.ssafy.matchup.user.feedback.dto.FeedbackDto;
import com.ssafy.matchup.user.feedback.dto.FeedbackScoreDto;
import com.ssafy.matchup.user.feedback.entity.Feedback;
import com.ssafy.matchup.user.feedback.repository.FeedbackRepository;
import com.ssafy.matchup.user.main.entity.User;
import com.ssafy.matchup.user.main.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public void writeFeedback(Long userId, FeedbackDto feedbackDto) {
        User feedbackingUser = userRepository.getReferenceById(userId);
        User feedbackedUser = userRepository.getReferenceById(feedbackDto.getFeedbackedUserId());
        Feedback feedback = Feedback.builder()
                .feedbackingUser(feedbackingUser)
                .feedbackedUser(feedbackedUser)
                .content(feedbackDto.getContent())
                .score(feedbackDto.getScore())
                .build();
        feedbackRepository.save(feedback);
    }

    @Override
    public List<FeedbackScoreDto> getFeedbacks(Long userId) {
        return feedbackRepository.findFeedbacksByFeedbackingUser_Id(userId).stream().map(FeedbackScoreDto::new).collect(toList());
    }
}
