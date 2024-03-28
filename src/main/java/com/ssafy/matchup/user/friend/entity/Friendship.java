package com.ssafy.matchup.user.friend.entity;

import com.ssafy.matchup.user.main.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "friendship")
@Getter
@NoArgsConstructor
public class Friendship {
    @Id
    @GeneratedValue
    @Column(name = "friendship_id")
    private Long id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private FriendStatus friendStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "my_id")
    private User myself;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_id")
    private User friend;

    @Builder
    public Friendship(User myself, User friend, FriendStatus friendStatus) {
        this.myself = myself;
        this.friend = friend;
        this.friendStatus = friendStatus;
    }

    @Builder
    public void updateFriendStatus(FriendStatus friendStatus) {
        this.friendStatus = friendStatus;
    }
}
