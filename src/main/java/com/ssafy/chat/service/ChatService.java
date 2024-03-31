package com.ssafy.chat.service;

import com.mongodb.client.MongoClient;
import com.ssafy.chat.dto.ChatDto;
import com.ssafy.chat.dto.ChatRoomDto;
import com.ssafy.chat.dto.FcmDto;
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
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {

    private final MongoTemplate mongoTemplate;
    private final KafkaTemplate<String, Chat> kafkaChatTemplate;
    private final KafkaTemplate<String, FcmDto> kafkaFcmTemplate;

    public void sendChat(String roomId, ChatDto chatDto) throws Exception {

        Chat chat = new Chat(roomId, chatDto.getUserId(), chatDto.getName(), chatDto.getIconUrl(), chatDto.getContent(), LocalDateTime.now(), false);
        log.error("몽고DB 세이브 : {}", mongoTemplate.save(chat));

        // Produce message to Kafka topic
        kafkaChatTemplate.send("chat", chat);

        Long receiverId = findReceiverId(roomId, chat.getUserId());
        FcmDto fcmDto = new FcmDto(chatDto.getName(), receiverId, "CHAT", chatDto.getContent());
        kafkaFcmTemplate.send("alarm", fcmDto);
    }

    public List<ChatRoomDto> findRooms(Long userId) {

        log.error("채팅방 목록 불러오기 userId : {}", userId);
        Query query = new Query(Criteria.where("participants").in(userId));
        List<ChatRoomDto> chatRoomDtoList = ChatMapper.instance.convertListChatRoomDto(mongoTemplate.find(query, ChatRoom.class));
        log.error("불러온 채팅방 개수 : {}", chatRoomDtoList.size());

        if (chatRoomDtoList != null) {
            for (ChatRoomDto chatRoomDto : chatRoomDtoList) {
                log.error("- roomId : {}", chatRoomDto.getRoomId());
                query = notReadQuery(chatRoomDto.getRoomId(), userId);
                chatRoomDto.setCnt(mongoTemplate.count(query, Chat.class));
            }
        }
        return chatRoomDtoList;
    }

    public List<ChatDto> findChattings(Long userId, String roomId) {

        log.error("채팅 내역 불러오기 ----------------------------");
        Query query = notReadQuery(roomId, userId);

        Update update = new Update();
        update.set("isRead", true);
        mongoTemplate.updateMulti(query, update, Chat.class);
        log.error("안읽은 메세지 읽음 처리-------------------------");

        query = new Query(Criteria.where("roomId").is(roomId));
        List<Chat> chats = mongoTemplate.find(query, Chat.class);
        for(Chat chat : chats) {
            log.error("- sender : {}",chat.getName());
            log.error("- content: {}",chat.getContent());
        }
        return ChatMapper.instance.convertListChatDto(chats);
    }

    public void createChatRoom(Long userId, ChatRoomDto chatRoomDto) throws Exception {

        if (!chatRoomDto.getParticipants().contains(userId)) {
            log.error("ILLEGAL REQUEST!!!!!!");
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
        if (chatRoom != null) {
            ChatRoomDto chatRoomDto = ChatMapper.instance.convertChatRoomDto(chatRoom);
            query = notReadQuery(chatRoom.getObjectId().toString(), userId);
            chatRoomDto.setCnt(mongoTemplate.count(query, Chat.class));
            return chatRoomDto;
        }

        return null;
    }

    public Long findReceiverId(String roomId, Long userId) {

        ObjectId objectId = new ObjectId(roomId);
        ChatRoom chatRoom = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(objectId)),ChatRoom.class);

        for(Long id : Objects.requireNonNull(chatRoom).getParticipants()) {
            if(!id.equals(userId)) return id;
        }
        return -1L;
    }

    public Query notReadQuery(String roomId, Long userId) {
        return new Query(Criteria.where("roomId").is(roomId)
                .andOperator(
                        Criteria.where("userId").ne(userId),
                        Criteria.where("isRead").is(false)
                ));
    }
}
