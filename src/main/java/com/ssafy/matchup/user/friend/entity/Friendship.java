package com.ssafy.matchup.user.friend.entity;

import com.ssafy.matchup.user.main.entity.User;
import jakarta.persistence.*;

import java.util.List;

@Entity(name = "friendship")
public class Friendship {
    @Id
    @GeneratedValue
    @Column(name = "friendship_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "my_id")
    private User myself;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_id")
    private User friend;
}
