package com.ssafy.matchup.user.test;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import org.springframework.data.querydsl.binding.QuerydslPredicate;

@Data
public class HelloQueryDto {
    String name;
    Integer age;

    @QueryProjection
    public HelloQueryDto(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
