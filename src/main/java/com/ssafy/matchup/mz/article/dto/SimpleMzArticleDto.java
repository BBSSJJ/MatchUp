package com.ssafy.matchup.mz.article.dto;

import com.ssafy.matchup.mz.article.entity.MzArticle;
import com.ssafy.matchup.user.main.dto.UserDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SimpleMzArticleDto {
    private Long id;
    private String title;
    private String leftSympathyTitle;
    private String rightSympathyTitle;
    private String thumbnailUrl;
    private Long views;
    private UserDto author;
    private int leftSympathyCount;
    private int rightSympathyCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public SimpleMzArticleDto(MzArticle mzArticle) {
        this.id = mzArticle.getId();
        this.title = mzArticle.getTitle();
        this.leftSympathyTitle = mzArticle.getLeftSympathyTitle();
        this.rightSympathyTitle = mzArticle.getRightSympathyTitle();
        this.thumbnailUrl = mzArticle.getThumbnailUrl();
        this.views = mzArticle.getViews();
        this.author = new UserDto(mzArticle.getAuthor());
        this.leftSympathyCount = mzArticle.getLeftSympathies().size();
        this.rightSympathyCount = mzArticle.getRightSympathies().size();
        this.createdAt = mzArticle.getCreatedAt();
        this.updatedAt = mzArticle.getUpdatedAt();
    }
}
