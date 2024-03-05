package com.ssafy.matchup.mz.article.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ArticleContent {
    @Column(name = "content")
    private String content;
    @Column(name = "video_url")
    private String videoUrl;
    @Column(name = "thumbnail_url")
    private String thumbnailUrl;
    @Column(name = "left_sympathy_title")
    private String leftSympathyTitle;
    @Column(name = "right_sympathy_title")
    private String rightSympathyTitle;
}
