package com.ssafy.matchup.mz.sympathy.entity;

import com.ssafy.matchup.mz.article.entity.MzArticle;
import com.ssafy.matchup.user.main.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "sympathy")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Sympathy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sympathy_id")
    private Long Id;

    @Column(name = "left_right")
    @Enumerated(EnumType.STRING)
    private SympathyType sympathyType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mz_article_left_id")
    private MzArticle mzArticleLeft;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mz_article_right_id")
    private MzArticle mzArticleRight;
}
