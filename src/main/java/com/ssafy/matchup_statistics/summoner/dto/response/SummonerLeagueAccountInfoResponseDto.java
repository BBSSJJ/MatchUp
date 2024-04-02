package com.ssafy.matchup_statistics.summoner.dto.response;

import com.ssafy.matchup_statistics.global.dto.response.AccountResponseDto;
import com.ssafy.matchup_statistics.global.dto.response.LeagueInfoResponseDto;
import com.ssafy.matchup_statistics.global.dto.response.SummonerInfoResponseDto;
import com.ssafy.matchup_statistics.summoner.entity.Summoner;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SummonerLeagueAccountInfoResponseDto {
    private SummonerInfoResponseDto summonerInfoDto;
    private LeagueInfoResponseDto leagueInfoDto;
    private AccountResponseDto accountResponseDto;

    public SummonerLeagueAccountInfoResponseDto(Summoner summoner) {
        this.summonerInfoDto = new SummonerInfoResponseDto(summoner);
        this.leagueInfoDto = new LeagueInfoResponseDto(summoner.getLeague());
        this.accountResponseDto = new AccountResponseDto(summoner.getAccount());
    }
}
