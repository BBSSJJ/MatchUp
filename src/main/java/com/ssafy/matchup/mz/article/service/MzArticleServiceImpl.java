package com.ssafy.matchup.mz.article.service;

import com.ssafy.matchup.global.dto.ListDto;
import com.ssafy.matchup.mz.article.dto.MzArticleDto;
import com.ssafy.matchup.mz.article.dto.SimpleMzArticleDto;
import com.ssafy.matchup.mz.article.dto.request.WriteMzArticleRequestDto;
import com.ssafy.matchup.mz.article.entity.MzArticle;
import com.ssafy.matchup.mz.article.repository.MzArticleRepository;
import com.ssafy.matchup.mz.sympathy.dto.SympathyDto;
import com.ssafy.matchup.mz.sympathy.repository.SympathyRepository;
import com.ssafy.matchup.user.main.entity.User;
import com.ssafy.matchup.user.main.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
public class MzArticleServiceImpl implements MzArticleService {

//    private final int PAGE_SIZE = 10;

    private final MzArticleRepository mzArticleRepository;
    private final SympathyRepository sympathyRepository;
    private final UserRepository userRepository;

//    @Transactional
//    @Override
//    public Page<SimpleMzArticleDto> listMzArticle(int page, String criteria) {
//        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.Direction.DESC, criteria);
//        Page<MzArticle> articles = mzArticleRepository.findAll(pageable);
//        return articles.map(SimpleMzArticleDto::new);
//    }
//
//    @Transactional
//    @Override
//    public Page<SimpleMzArticleDto> listMyMzArticle(Long userId, int page, String criteria) {
//        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.Direction.DESC, criteria);
//        User user = userRepository.getReferenceById(userId);
//        Page<MzArticle> articles = mzArticleRepository.findMzArticlesByAuthor(user, pageable);
//        return articles.map(SimpleMzArticleDto::new);
//    }

    @Transactional
    @Override
    public void addMzArticle(Long userId, WriteMzArticleRequestDto writeMzArticleRequestDto) {
        User author = userRepository.getReferenceById(userId);
        MzArticle mzArticle = new MzArticle(writeMzArticleRequestDto.getTitle(),
                writeMzArticleRequestDto.getContent(),
                writeMzArticleRequestDto.getLeftSympathyTitle(),
                writeMzArticleRequestDto.getRightSympathyTitle(),
                author
        );
        mzArticleRepository.save(mzArticle);
    }

    @Transactional
    @Override
    public MzArticleDto detailMzArticle(Long articleId, boolean firstRead) {
        Optional<MzArticle> mzArticleOptional = mzArticleRepository.findMzArticleById(articleId);
        if (mzArticleOptional.isEmpty()) throw new EntityNotFoundException();
        MzArticle mzArticle = mzArticleOptional.get();

        log.info("mzArticle : {}", mzArticle);

        if (firstRead)
            mzArticleRepository.updateViews(articleId);

        List<SympathyDto> leftSympathies = sympathyRepository.findLeftSympathiesByMzArticleId(articleId)
                .stream().map(SympathyDto::new).collect(toList());
        List<SympathyDto> rightSympathies = sympathyRepository.findRightSympathiesByMzArticleId(articleId)
                .stream().map(SympathyDto::new).collect(toList());
        MzArticleDto mzArticleDto = new MzArticleDto(mzArticle);
        mzArticleDto.setSympathies(leftSympathies, rightSympathies);

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
        if (!articleId.equals(mzArticle.getAuthor().getId())) throw new SecurityException();
        mzArticle.updateMzArticle(writeMzArticleRequestDto.getTitle(),
                writeMzArticleRequestDto.getContent(),
                writeMzArticleRequestDto.getLeftSympathyTitle(),
                writeMzArticleRequestDto.getRightSympathyTitle()
        );
    }

    @Transactional
    @Override
    public ListDto<SimpleMzArticleDto> listMzArticle(String title) {
        if (title == null) title = "";
        List<SimpleMzArticleDto> articles = mzArticleRepository.findMzArticlesByTitleContaining(title).stream()
                .map(SimpleMzArticleDto::new).collect(toList());
        return new ListDto<>(articles);
    }

    @Transactional
    @Override
    public ListDto<SimpleMzArticleDto> listMyMzArticle(Long userId) {
        List<SimpleMzArticleDto> articles = mzArticleRepository.findMzArticlesByAuthorId(userId).stream()
                .map(SimpleMzArticleDto::new).collect(toList());
        return new ListDto<>(articles);
    }
}
