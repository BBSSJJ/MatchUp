package com.ssafy.matchup.user.main.entity;

import com.ssafy.matchup.mz.article.entity.MzArticle;
import com.ssafy.matchup.user.feedback.entity.Feedback;
import com.ssafy.matchup.user.friend.entity.Friendship;
import com.ssafy.matchup.user.main.entity.type.AuthorityType;
import com.ssafy.matchup.user.main.entity.type.SnsType;
import com.ssafy.matchup.user.riotaccount.entity.RiotAccount;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(name = "sns_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private SnsType snsType;

    @Column(name = "sns_id", nullable = false)
    private String snsId;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthorityType role;

    @Embedded
    private Setting setting;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private RiotAccount riotAccount;

//    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
//    private Lbti lbti;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "myself", orphanRemoval = true)
    private List<Friendship> friends = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "feedbackingUser")
    private final List<Feedback> feedbacking = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "feedbackedUser")
    private final List<Feedback> feedbacked = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    private final List<MzArticle> mzArticles = new ArrayList<>();

    public void updateRiotAccount(RiotAccount riotAccount) {
        this.riotAccount = riotAccount;
    }

    public void updateSetting(Setting setting) {
        this.setting = setting;
    }
}
