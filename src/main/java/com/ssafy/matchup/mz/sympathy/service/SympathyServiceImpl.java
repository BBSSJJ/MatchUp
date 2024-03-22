package com.ssafy.matchup.mz.sympathy.service;

import com.ssafy.matchup.mz.article.entity.MzArticle;
import com.ssafy.matchup.mz.article.repository.MzArticleRepository;
import com.ssafy.matchup.mz.sympathy.dto.request.UpdateSympathyRequestDto;
import com.ssafy.matchup.mz.sympathy.entity.Sympathy;
import com.ssafy.matchup.mz.sympathy.entity.SympathyType;
import com.ssafy.matchup.mz.sympathy.repository.SympathyRepository;
import com.ssafy.matchup.user.main.entity.User;
import com.ssafy.matchup.user.main.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SympathyServiceImpl implements SympathyService {
    private final SympathyRepository sympathyRepository;
    private final UserRepository userRepository;
    private final MzArticleRepository mzArticleRepository;

    @Transactional
    @Override
    public void updateSympathy(Long userId, Long articleId, UpdateSympathyRequestDto updateSympathyRequestDto) {
        User user = userRepository.getReferenceById(userId);
        MzArticle mzArticle = mzArticleRepository.getReferenceById(articleId);
        Optional<Sympathy> leftSympathyOptional = sympathyRepository.findSympathyByUserAndMzArticleLeft(user, mzArticle);
        Optional<Sympathy> rightSympathyOptional = sympathyRepository.findSympathyByUserAndMzArticleRight(user, mzArticle);
        SympathyType sympathyType = updateSympathyRequestDto.getSympathyType();

        if (sympathyType.equals(SympathyType.LEFT)) {
            if (leftSympathyOptional.isEmpty() && rightSympathyOptional.isEmpty()) {
                //생성
                Sympathy sympathy = Sympathy.builder()
                        .sympathyType(sympathyType)
                        .user(user)
                        .mzArticleLeft(mzArticle)
                        .build();
                mzArticle.getLeftSympathies().add(sympathy);
            } else if (rightSympathyOptional.isPresent()) {
                mzArticle.getRightSympathies().remove(rightSympathyOptional.get());
                sympathyRepository.delete(rightSympathyOptional.get());
                sympathyRepository.flush();
                Sympathy sympathy = Sympathy.builder()
                        .sympathyType(sympathyType)
                        .user(user)
                        .mzArticleLeft(mzArticle)
                        .build();
                mzArticle.getLeftSympathies().add(sympathy);
            } else {
                //제거
                mzArticle.getLeftSympathies().remove(leftSympathyOptional.get());
            }
        } else if (sympathyType.equals(SympathyType.RIGHT)) {
            if (leftSympathyOptional.isEmpty() && rightSympathyOptional.isEmpty()) {
                //생성
                Sympathy sympathy = Sympathy.builder()
                        .sympathyType(sympathyType)
                        .user(user)
                        .mzArticleRight(mzArticle)
                        .build();
                mzArticle.getRightSympathies().add(sympathy);
            } else if (leftSympathyOptional.isPresent()) {
                mzArticle.getLeftSympathies().remove(leftSympathyOptional.get());
                sympathyRepository.delete(leftSympathyOptional.get());
                sympathyRepository.flush();
                Sympathy sympathy = Sympathy.builder()
                        .sympathyType(sympathyType)
                        .user(user)
                        .mzArticleRight(mzArticle)
                        .build();
                mzArticle.getRightSympathies().add(sympathy);
            } else {
                //제거
                mzArticle.getRightSympathies().remove(rightSympathyOptional.get());
            }
        }

    }
}
