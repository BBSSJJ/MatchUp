package com.ssafy.matchup.user.main.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SummonerLeagueAccountInfoResponseDto {
    private SummonerInfoDto summonerInfoDto;
    private LeagueInfoDto leagueInfoDto;
    private AccountResponseDto accountResponseDto;
}
