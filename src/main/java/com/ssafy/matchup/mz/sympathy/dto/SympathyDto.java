package com.ssafy.matchup.mz.sympathy.dto;

import com.ssafy.matchup.mz.sympathy.entity.Sympathy;
import lombok.Builder;
import lombok.Data;

@Data
public class SympathyDto {
    private Long id;
    private Long userId;

    @Builder
    public SympathyDto(Sympathy sympathy) {
        this.id = sympathy.getId();
        this.userId = sympathy.getUser().getId();
    }
}
