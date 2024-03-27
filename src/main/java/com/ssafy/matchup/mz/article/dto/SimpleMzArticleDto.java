package com.ssafy.matchup.mz.article.dto;

import com.ssafy.matchup.mz.article.entity.MzArticle;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SimpleMzArticleDto {
    private Long id;
    private String title;
    private String leftSympathyTitle;
    private String rightSympathyTitle;
    private Long views;
//    private UserDto author;
    private String author;
    private Long userId;
    private String tag;
    private String iconUrl;
    private String tier;
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
        this.views = mzArticle.getViews();
        this.author = mzArticle.getAuthor().getRiotAccount().getSummonerProfile().getName();
        this.userId = mzArticle.getAuthor().getId();
        this.tag = "#" + mzArticle.getAuthor().getRiotAccount().getSummonerProfile().getTag();
        this.iconUrl = mzArticle.getAuthor().getRiotAccount().getSummonerProfile().getIconUrl();
        this.tier = mzArticle.getAuthor().getRiotAccount().getTier();
        this.leftSympathyCount = mzArticle.getLeftSympathies().size();
        this.rightSympathyCount = mzArticle.getRightSympathies().size();
        this.createdAt = mzArticle.getCreatedAt();
        this.updatedAt = mzArticle.getUpdatedAt();
    }
}
