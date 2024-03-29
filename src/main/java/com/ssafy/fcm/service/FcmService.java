package com.ssafy.fcm.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.ssafy.chat.dto.FcmDto;
import com.ssafy.fcm.entity.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FcmService {

    private final MongoTemplate mongoTemplate;

    public void registClientToken(Long userId, String clientToken) {

        mongoTemplate.save(new Token(userId, clientToken));
    }

    @KafkaListener(topics = "alarm", containerFactory = "kafkaListenerContainerFactory") // 추가: 컨테이너 팩토리를 지정합니다.
    public void listenFcm(FcmDto fcmDto) throws FirebaseMessagingException {

        log.error("- sender : {}", fcmDto.getSender());
        log.error("- receiver : {}", fcmDto.getReceiverId());
        log.error("- type : {}", fcmDto.getType());
        log.error("- content : {}", fcmDto.getContent());

        String registrationToken = getClientToken(fcmDto.getReceiverId());
        log.error("- Token : {}", registrationToken);

        Message message = Message.builder()
                .putData("sender", fcmDto.getSender())
                .putData("type", fcmDto.getType())
                .putData("content", fcmDto.getContent())
                .setToken(registrationToken)
                .build();

        FirebaseMessaging.getInstance().send(message);
    }

    public String getClientToken(Long userId) {

        Token token = mongoTemplate.findOne(Query.query(Criteria.where("userId").is(userId)), Token.class);
        if (token == null)
            return null;
        return token.getClientToken();
    }
}
