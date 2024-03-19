package com.ssafy.matchup.user.main.dto.request;

import com.ssafy.matchup.user.main.entity.type.AuthorityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistUserRequestDto {
    String snsId;
    AuthorityType role;
    String name;
}
