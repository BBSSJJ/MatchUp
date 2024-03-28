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
import org.springframework.data.mongodb.core.query.Update;
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

        Chat chat = new Chat(id, chatDto.getUserId(), chatDto.getName(), chatDto.getIconUrl(), chatDto.getContent(), LocalDateTime.now(), false);
        mongoTemplate.save(chat);

        // Produce message to Kafka topic
        kafkaTemplate.send("chat", chat);
    }

    public List<ChatRoomDto> findRooms(Long userId) {

        Query query = new Query(Criteria.where("participants").in(userId));
        List<ChatRoomDto> chatRoomDtoList = ChatMapper.instance.convertListChatRoomDto(mongoTemplate.find(query, ChatRoom.class));
        if(chatRoomDtoList != null) {
            for(ChatRoomDto chatRoomDto : chatRoomDtoList) {
                query = new Query(Criteria.where("roomId").is(chatRoomDto.getRoomId())
                        .andOperator(
                                Criteria.where("userId").ne(userId),
                                Criteria.where("isRead").is(false)
                        ));
                chatRoomDto.setCnt(mongoTemplate.count(query, Chat.class));
            }
        }
        return chatRoomDtoList;
    }

    public List<ChatDto> findChattings(Long userId, String roomId) {

        Query query = new Query(Criteria.where("roomId").is(roomId)
                .andOperator(
                        Criteria.where("userId").ne(userId),
                        Criteria.where("isRead").is(false)
                ));

        Update update = new Update();
        update.set("isRead", true);
        mongoTemplate.updateMulti(query, update, Chat.class);
        
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

    public ChatRoomDto findRoom(Long userId, Long otherId) {

        Query query = new Query(Criteria.where("participants").all(userId, otherId));
        ChatRoom chatRoom = mongoTemplate.findOne(query, ChatRoom.class);
        if(chatRoom == null) {
            log.error("chatRoom is null!");
        }

        return ChatMapper.instance.convertChatRoomDto(mongoTemplate.findOne(query, ChatRoom.class));
    }
}
