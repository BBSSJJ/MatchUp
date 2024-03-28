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
    private Long userId;
    private String name;
    private String iconUrl;
    private String content;
    private LocalDateTime timestamp;
    private boolean isRead;

}
