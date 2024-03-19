package com.ssafy.chat.mapper;

import com.ssafy.chat.dto.ChatDto;
import com.ssafy.chat.dto.ChatRoomDto;
import com.ssafy.chat.entity.Chat;
import com.ssafy.chat.entity.ChatRoom;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ChatMapper {

    ChatMapper instance = Mappers.getMapper(ChatMapper.class);

    ChatDto convertChatDto(Chat chat);

    List<ChatDto> convertListChatDto(List<Chat> chat);

    @Mapping(source = "objectId", target = "roomId", qualifiedByName = "objectIdToString")
    ChatRoomDto convertChatRoomDto(ChatRoom chatRoom);

    @Named("objectIdToString")
    default String objectIdToString(ObjectId objectId) {
        return objectId != null ? objectId.toString() : null;
    }

    List<ChatRoomDto> convertListChatRoomDto(List<ChatRoom> chatRooms);

}
