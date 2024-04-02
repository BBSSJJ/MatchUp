package com.ssafy.matchup.user.main.dto.response;

import com.ssafy.matchup.user.main.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserInTierResponseDto {
    Long userId;
    String riotId;

    @Builder
    public UserInTierResponseDto(User user) {
        this.userId = user.getId();
        this.riotId = user.getRiotAccount().getId();
    }
}
