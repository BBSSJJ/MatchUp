package com.ssafy.matchup.user.friend.repository;

import com.ssafy.matchup.user.friend.entity.FriendStatus;
import com.ssafy.matchup.user.friend.entity.Friendship;
import com.ssafy.matchup.user.main.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    @Query("SELECT fs.friend FROM com.ssafy.matchup.user.friend.entity.Friendship fs " +
            "JOIN FETCH fs.friend.riotAccount ra " +
            "JOIN FETCH ra.summonerProfile " +
            "WHERE fs.myself.id = :id AND fs.friendStatus = :friendStatus")
    List<User> findFriendByMyself_IdAndFriendStatus(Long id, FriendStatus friendStatus);

    @Query(value = "SELECT fs FROM com.ssafy.matchup.user.friend.entity.Friendship fs " +
            "WHERE fs.myself.id = :myId " +
            "AND fs.friend.id = :friendId")
    Friendship findByMyIdAndFriendId(Long myId, Long friendId);

}
