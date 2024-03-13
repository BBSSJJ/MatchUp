package com.ssafy.matchup.user.oauth2.dto;

import com.ssafy.matchup.user.main.entity.type.AuthorityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtTokenRequestDto {
    Long id;
    AuthorityType role;
}
