package com.ssafy.matchup.mz.sympathy.dto.request;

import com.ssafy.matchup.mz.sympathy.entity.SympathyType;
import lombok.Data;

@Data
public class UpdateSympathyRequestDto {
    SympathyType sympathyType;
}
