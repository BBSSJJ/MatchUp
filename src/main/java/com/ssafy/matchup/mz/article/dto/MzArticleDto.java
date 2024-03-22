package com.ssafy.matchup.mz.article.dto;

import com.ssafy.matchup.mz.article.entity.MzArticle;
import com.ssafy.matchup.mz.sympathy.dto.SympathyDto;
import com.ssafy.matchup.user.main.dto.UserDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MzArticleDto {
    private Long id;
    private String title;
    private String content;
    private String leftSympathyTitle;
    private String rightSympathyTitle;
    private String thumbnailUrl;
    private Long views;
    private UserDto author;
    private List<SympathyDto> leftSympathies;
    private List<SympathyDto> rightSympathies;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public MzArticleDto(MzArticle mzArticle) {
        this.id = mzArticle.getId();
        this.title = mzArticle.getTitle();
        this.content = mzArticle.getContent();
        this.leftSympathyTitle = mzArticle.getLeftSympathyTitle();
        this.rightSympathyTitle = mzArticle.getRightSympathyTitle();
        this.thumbnailUrl = mzArticle.getThumbnailUrl();
        this.views = mzArticle.getViews();
        this.author = new UserDto(mzArticle.getAuthor());
        this.createdAt = mzArticle.getCreatedAt();
        this.updatedAt = mzArticle.getUpdatedAt();
    }

    public void setSympathies(List<SympathyDto> leftSympathies, List<SympathyDto> rightSympathies) {
        this.leftSympathies = leftSympathies;
        this.rightSympathies = rightSympathies;
    }
}
