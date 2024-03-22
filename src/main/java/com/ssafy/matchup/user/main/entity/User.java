package com.ssafy.matchup.user.main.entity;

import com.ssafy.matchup.user.feedback.entity.Feedback;
import com.ssafy.matchup.user.friend.entity.Friendship;
import com.ssafy.matchup.user.lbti.entity.Lbti;
import com.ssafy.matchup.user.main.entity.type.AuthorityType;
import com.ssafy.matchup.user.main.entity.type.SnsType;
import com.ssafy.matchup.user.riotaccount.entity.RiotAccount;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private RiotAccount riotAccount;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private Lbti lbti;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "friend")
    private List<Friendship> friends;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "feedBackedUser")
    private final List<Feedback> feedbacked = new ArrayList<>();

}
