package com.ssafy.matchup.mz.article.entity;

import com.ssafy.matchup.global.entity.BaseTimeEntity;
import com.ssafy.matchup.mz.comment.entity.Comment;
import com.ssafy.matchup.mz.sympathy.entity.Sympathy;
import com.ssafy.matchup.user.main.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "mz_article")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MzArticle extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mz_article_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "left_sympathy_title")
    private String leftSympathyTitle;

    @Column(name = "right_sympathy_title")
    private String rightSympathyTitle;

    @Column(name = "views")
    private Long views = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "mzArticleLeft")
    private final List<Sympathy> leftSympathies = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "mzArticleRight")
    private final List<Sympathy> rightSympathies = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "mzArticle")
    private final List<Comment> comments = new ArrayList<>();


    @Builder
    public MzArticle(String title, String content, String leftSympathyTitle, String rightSympathyTitle,
                     String thumbnailUrl, User author) {
        this.title = title;
        this.content = content;
        this.leftSympathyTitle = leftSympathyTitle;
        this.rightSympathyTitle = rightSympathyTitle;
        this.author = author;
    }

    @Builder
    public void updateMzArticle(String title, String content, String leftSympathyTitle, String rightSympathyTitle) {
        this.title = title;
        this.content = content;
        this.leftSympathyTitle = leftSympathyTitle;
        this.rightSympathyTitle = rightSympathyTitle;
    }
}
