package com.ssafy.matchup.mz.sympathy.entity;

import com.ssafy.matchup.mz.article.entity.MzArticle;
import com.ssafy.matchup.user.main.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "sympathy")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mz_article_id")
    private MzArticle mzArticle;
}
