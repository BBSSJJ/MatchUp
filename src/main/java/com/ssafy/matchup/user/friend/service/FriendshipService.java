package com.ssafy.matchup.user.friend.service;

import com.ssafy.matchup.user.friend.entity.Friendship;
import com.ssafy.matchup.user.friend.repository.FriendshipRepository;
import com.ssafy.matchup.user.main.dto.UserDto;
import com.ssafy.matchup.user.main.entity.User;
import com.ssafy.matchup.user.main.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class FriendshipService {

    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;

    public List<UserDto> showFriendsList(Long myId) {

        List<UserDto> friendsList = new ArrayList<>();
        List<User> friendshipList = friendshipRepository.findAllByMyId(myId);

        for (User friend : friendshipList) {
            friendsList.add(new UserDto(friend));
        }

        return friendsList;
    }

    public void registFriendship(Long myId, Long friendId) {

        if (friendshipRepository.findByMyIdAndFriendId(myId, friendId) == null) {
            throw new InternalError("already exist friendship");
        }
        User myself = userRepository.getReferenceById(myId);
        User friend = userRepository.getReferenceById(friendId);
        Friendship friendship1 = new Friendship(myself, friend);
        Friendship friendship2 = new Friendship(friend, myself);
        friendshipRepository.save(friendship1);
        friendshipRepository.save(friendship2);
    }

    public void deleteFriendship(Long myId, Long friendId) {

        Friendship friendship1 = friendshipRepository.findByMyIdAndFriendId(myId, friendId);
        Friendship friendship2 = friendshipRepository.findByMyIdAndFriendId(friendId, myId);
        if (friendship1 == null || friendship2 == null) {
            throw new InternalError("not exist friendship");
        }
        friendshipRepository.delete(friendship1);
        friendshipRepository.delete(friendship2);

    }
}
