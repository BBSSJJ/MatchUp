package com.ssafy.matchup.mz.article.service;

import com.ssafy.matchup.global.dto.ListDto;
import com.ssafy.matchup.mz.article.dto.MzArticleDto;
import com.ssafy.matchup.mz.article.dto.SimpleMzArticleDto;
import com.ssafy.matchup.mz.article.dto.request.WriteMzArticleRequestDto;

public interface MzArticleService {
    ListDto<SimpleMzArticleDto> listMzArticle(int pageNo, String criteria);

    void addMzArticle(Long userId, WriteMzArticleRequestDto writeMzArticleRequestDto);

    MzArticleDto detailMzArticle(Long articleId);

    void deleteMzArticle(Long articleId, Long userId);

    void updateMzArticle(Long articleId, Long userId, WriteMzArticleRequestDto writeMzArticleRequestDto);
}
