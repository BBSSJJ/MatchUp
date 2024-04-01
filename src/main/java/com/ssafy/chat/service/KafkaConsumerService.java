package com.ssafy.chat.service;

import com.ssafy.chat.dto.RecruitDto;
import com.ssafy.chat.entity.Chat;
import com.ssafy.chat.mapper.ChatMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @KafkaListener(topics = "chat", containerFactory = "kafkaChatListenerContainerFactory") // 추가: 컨테이너 팩토리를 지정합니다.
    public void listenChat(Chat chat) {
        simpMessagingTemplate.convertAndSend("/topic/" + chat.getRoomId(), ChatMapper.instance.convertChatDto(chat));
    }

    @KafkaListener(topics = "recruit", containerFactory = "kafkaRecruitListenerContainerFactory")
    // 추가: 컨테이너 팩토리를 지정합니다.
    public void listenRecruit(RecruitDto recruitDto) {
        simpMessagingTemplate.convertAndSend("/topic/recruit", recruitDto);
    }

}