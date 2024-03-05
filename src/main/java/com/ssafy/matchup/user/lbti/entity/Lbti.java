package com.ssafy.matchup.user.lbti.entity;

import com.ssafy.matchup.user.main.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "lbti")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lbti {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lbti_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
