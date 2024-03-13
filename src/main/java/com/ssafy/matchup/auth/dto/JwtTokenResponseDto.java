package com.ssafy.matchup.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtTokenResponseDto {
    private String accessToken;
    private String refreshToken;
}
