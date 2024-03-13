package com.ssafy.matchup.auth.dto;

import com.ssafy.matchup.auth.dto.type.AuthorityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtTokenRequestDto {
    private Long id;
    private AuthorityType role;
}
