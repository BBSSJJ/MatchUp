package com.ssafy.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "chatrooms")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {

    @Id
    private ObjectId objectId;
    private ArrayList<Long> participants = new ArrayList<>();

    public ChatRoom(ArrayList<Long> participants) {
        this.participants = participants;
    }

}
