package com.ssafy.matchup.user.friend.service;

import com.ssafy.matchup.global.dto.FcmDto;
import com.ssafy.matchup.user.friend.entity.FriendStatus;
import com.ssafy.matchup.user.friend.entity.Friendship;
import com.ssafy.matchup.user.friend.repository.FriendshipRepository;
import com.ssafy.matchup.user.main.dto.UserDto;
import com.ssafy.matchup.user.main.entity.User;
import com.ssafy.matchup.user.main.repository.UserRepository;
import com.ssafy.matchup.user.riotaccount.entity.SummonerProfile;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class FriendshipService {
    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;
    private final KafkaTemplate<String, FcmDto> kafkaTemplate;

    public List<UserDto> showFriendList(Long myId, FriendStatus friendStatus) {
        List<User> friendshipList = friendshipRepository.findFriendByMyself_IdAndFriendStatus(myId, friendStatus);
        return friendshipList.stream().map(UserDto::new).collect(toList());
    }

    public List<UserDto> showUserList(String keyword) {
        return userRepository.findUserByKeyword(keyword);
    }

    public void sendFriendRequest(Long myId, Long friendId) {

        if (friendshipRepository.findByMyIdAndFriendId(myId, friendId) != null) {
            throw new InternalError("already exist friendship");
        }
        User myself = userRepository.findUserById(myId).orElseThrow(EntityNotFoundException::new);
        User friend = userRepository.getReferenceById(friendId);
        Friendship friendship1 = new Friendship(myself, friend, FriendStatus.SENT);
        Friendship friendship2 = new Friendship(friend, myself, FriendStatus.RECEIVED);
        friendshipRepository.save(friendship1);
        friendshipRepository.save(friendship2);

        SummonerProfile summonerProfile = myself.getRiotAccount().getSummonerProfile();
        String name = summonerProfile.getName()+"#"+summonerProfile.getTag();
        String content = name + "님이 친구를 요청하셨습니다.";
        FcmDto fcmDto = new FcmDto(name, summonerProfile.getIconUrl(), friendId, "FRIEND", content);
        kafkaTemplate.send("alarm", fcmDto);
    }

    public void allowFriendRequest(Long myId, Long friendId) {
        Friendship friendship1 = friendshipRepository.findByMyIdAndFriendId(myId, friendId);
        Friendship friendship2 = friendshipRepository.findByMyIdAndFriendId(friendId, myId);

        if (friendship1 == null || friendship2 == null) throw new InternalError("invalid request");
        if (friendship1.getFriendStatus() != FriendStatus.RECEIVED ||
                friendship2.getFriendStatus() != FriendStatus.SENT) throw new InternalError("invalid request");

        friendship1.updateFriendStatus(FriendStatus.FRIEND);
        friendship2.updateFriendStatus(FriendStatus.FRIEND);

        User myself = userRepository.findUserById(myId).orElseThrow(EntityNotFoundException::new);
        SummonerProfile summonerProfile = myself.getRiotAccount().getSummonerProfile();
        String name = summonerProfile.getName()+"#"+summonerProfile.getTag();
        String content = name + "님이 친구 요청을 수락하셨습니다.";
        FcmDto fcmDto = new FcmDto(name, summonerProfile.getIconUrl(), friendId, "FRIEND", content);
        kafkaTemplate.send("alarm", fcmDto);
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
