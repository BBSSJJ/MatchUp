package com.ssafy.fcm.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tokens")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Token {

    @Id
    private Long userId;
    private String clientToken;

}
