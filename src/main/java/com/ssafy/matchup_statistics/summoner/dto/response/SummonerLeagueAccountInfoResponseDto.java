package com.ssafy.matchup_statistics.summoner.dto.response;

import com.ssafy.matchup_statistics.global.dto.response.AccountResponseDto;
import com.ssafy.matchup_statistics.global.dto.response.LeagueInfoResponseDto;
import com.ssafy.matchup_statistics.global.dto.response.SummonerInfoResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SummonerLeagueAccountInfoResponseDto {
    private SummonerInfoResponseDto summonerInfoDto;
    private LeagueInfoResponseDto leagueInfoDto;
    private AccountResponseDto accountResponseDto;
}
