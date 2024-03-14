package com.ssafy.matchup.user.test;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class HelloQueryDsl {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Integer age;
}
