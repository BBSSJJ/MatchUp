package com.ssafy.chat.service;

import com.ssafy.chat.dto.ChatDto;
import com.ssafy.chat.dto.ChatRoomDto;
import com.ssafy.chat.entity.Chat;
import com.ssafy.chat.entity.ChatRoom;
import com.ssafy.chat.mapper.ChatMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {

    private final MongoTemplate mongoTemplate;
    private final KafkaTemplate<String, Chat> kafkaTemplate;

    public void sendChat(String id, ChatDto chatDto) throws Exception {

        Chat chat = new Chat(id, chatDto.getUserId(), chatDto.getName(), chatDto.getIconUrl(), chatDto.getContent(), LocalDateTime.now());
        mongoTemplate.save(chat);

        // Produce message to Kafka topic
        kafkaTemplate.send("chat", chat);
    }

    public List<ChatRoomDto> findRooms(Long userId) {

        Query query = new Query();
        query.addCriteria(Criteria.where("participants").in(userId));

        return ChatMapper.instance.convertListChatRoomDto(mongoTemplate.find(query, ChatRoom.class));
    }

    public List<ChatDto> findChattings(Long userId, String roomId) {

        Query query = new Query();
        query.addCriteria(Criteria.where("roomId").is(roomId));
//        query.addCriteria(Criteria.where("participants").in(userId));

        return ChatMapper.instance.convertListChatDto(mongoTemplate.find(query, Chat.class));
    }

    public void createChatRoom(Long userId, ChatRoomDto chatRoomDto) throws Exception {

        if (!chatRoomDto.getParticipants().contains(userId)) {
            throw new Exception("illegal request");
        }

        ChatRoom chatRoom = new ChatRoom(chatRoomDto.getParticipants());

        mongoTemplate.save(chatRoom);
    }

    public void deleteChatRoom(Long userId, String roomId) throws Exception {

        ObjectId objectId = new ObjectId(roomId);
        ChatRoom chatRoom = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(objectId)), ChatRoom.class);
        if (chatRoom != null && !chatRoom.getParticipants().contains(userId)) {
            throw new Exception("illegal request");
        }

        mongoTemplate.remove(Query.query(Criteria.where("_id").is(objectId)), ChatRoom.class);
    }
}
