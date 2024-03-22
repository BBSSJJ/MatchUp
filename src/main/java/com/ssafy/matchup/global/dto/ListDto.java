package com.ssafy.matchup.global.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class ListDto<T> {
    private List<T> list;

    @Builder
    public ListDto(List<T> list) {
        this.list = list;
    }
}
