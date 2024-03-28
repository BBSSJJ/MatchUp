package com.ssafy.matchup.user.friend.repository;

import com.ssafy.matchup.user.friend.entity.Friendship;
import com.ssafy.matchup.user.main.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    @Query(value = "SELECT fs.friend FROM com.ssafy.matchup.user.friend.entity.Friendship fs " +
            "JOIN FETCH fs.friend.riotAccount ra " +
            "WHERE fs.myself.id = ?1")
    List<User> findAllByMyId(Long myId);

    @Query(value = "SELECT fs FROM com.ssafy.matchup.user.friend.entity.Friendship fs " +
            "WHERE fs.myself.id = ?1 " +
            "AND fs.friend.id = ?2")
    Friendship findByMyIdAndFriendId(Long myId, Long FriendId);

}
