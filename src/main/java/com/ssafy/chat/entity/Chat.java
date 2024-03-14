package com.ssafy.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "chattings")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Chat {

    private String roomId;
    private String sender;
    private String content;
    private LocalDateTime timestamp;

}
