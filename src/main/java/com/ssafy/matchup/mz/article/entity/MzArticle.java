package com.ssafy.matchup.mz.article.entity;

import com.ssafy.matchup.mz.comment.entity.Comment;
import com.ssafy.matchup.mz.sympathy.entity.Sympathy;
import com.ssafy.matchup.user.main.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "mz_article")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MzArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mz_article_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Embedded
    private ArticleContent content;

    @Column(name = "views")
    private Long views;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mzArticle")
    private final List<Sympathy> sympathies = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mzArticle")
    private final List<Comment> comments = new ArrayList<>();
}
