package com.ssafy.matchup.mz.article.service;

import com.ssafy.matchup.global.dto.ListDto;
import com.ssafy.matchup.mz.article.dto.MzArticleDto;
import com.ssafy.matchup.mz.article.dto.SimpleMzArticleDto;
import com.ssafy.matchup.mz.sympathy.dto.SympathyDto;
import com.ssafy.matchup.mz.article.dto.request.WriteMzArticleRequestDto;
import com.ssafy.matchup.mz.article.entity.MzArticle;
import com.ssafy.matchup.mz.article.repository.MzArticleRepository;
import com.ssafy.matchup.mz.sympathy.repository.SympathyRepository;
import com.ssafy.matchup.user.main.entity.User;
import com.ssafy.matchup.user.main.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
public class MzArticleServiceImpl implements MzArticleService {

    private final int PAGE_SIZE = 10;

    private final MzArticleRepository mzArticleRepository;
    private final SympathyRepository sympathyRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public ListDto<SimpleMzArticleDto> listMzArticle(int pageNo, String criteria) {
        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE, Sort.Direction.DESC, criteria);
        List<SimpleMzArticleDto> simpleMzArticles = mzArticleRepository.findAll(pageable).stream()
                .map(SimpleMzArticleDto::new)
                .collect(toList());
        return new ListDto<>(simpleMzArticles);
    }

    @Transactional
    @Override
    public void addMzArticle(Long userId, WriteMzArticleRequestDto writeMzArticleRequestDto) {
        User author = userRepository.getReferenceById(userId);
        String thumbnailUrl = null;
        MzArticle mzArticle = new MzArticle(writeMzArticleRequestDto.getTitle(),
                writeMzArticleRequestDto.getContent(),
                writeMzArticleRequestDto.getLeftSympathyTitle(),
                writeMzArticleRequestDto.getRightSympathyTitle(),
                thumbnailUrl,
                author
        );
        mzArticleRepository.save(mzArticle);
    }

    @Transactional
    @Override
    public MzArticleDto detailMzArticle(Long articleId) {
        MzArticle mzArticle = mzArticleRepository.getReferenceById(articleId);
        log.info("mzArticle : {}", mzArticle);
        List<SympathyDto> leftSympathies = sympathyRepository.findLeftSympathiesByMzArticleId(articleId)
                .stream().map(SympathyDto::new).collect(toList());
        List<SympathyDto> rightSympathies = sympathyRepository.findRightSympathiesByMzArticleId(articleId)
                .stream().map(SympathyDto::new).collect(toList());
        MzArticleDto mzArticleDto = new MzArticleDto(mzArticle);
        mzArticleDto.setSympathies(leftSympathies, rightSympathies);
        // TODO : Cookie 이용해서 하루에 한번만 조회수 오르도록 구현
        mzArticleRepository.updateViews(articleId);
        return mzArticleDto;
    }

    @Transactional
    @Override
    public void deleteMzArticle(Long articleId, Long userId) {
        MzArticle mzArticle = mzArticleRepository.getReferenceById(articleId);
        if (!userId.equals(mzArticle.getAuthor().getId())) throw new SecurityException();
        mzArticleRepository.delete(mzArticle);
    }

    @Transactional
    @Override
    public void updateMzArticle(Long articleId, Long userId, WriteMzArticleRequestDto writeMzArticleRequestDto) {
        MzArticle mzArticle = mzArticleRepository.getReferenceById(articleId);
        // TODO : 썸네일 변경 로직 작성
        String thumbnailUrl = mzArticle.getThumbnailUrl();
        if (!articleId.equals(mzArticle.getAuthor().getId())) throw new SecurityException();
        mzArticle.updateMzArticle(writeMzArticleRequestDto.getTitle(),
                writeMzArticleRequestDto.getContent(),
                writeMzArticleRequestDto.getLeftSympathyTitle(),
                writeMzArticleRequestDto.getRightSympathyTitle(),
                thumbnailUrl
        );
    }
}
