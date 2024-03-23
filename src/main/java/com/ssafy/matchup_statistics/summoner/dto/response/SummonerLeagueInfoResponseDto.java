package com.ssafy.matchup_statistics.summoner.dto.response;

import com.ssafy.matchup_statistics.global.dto.response.SummonerInfoResponseDto;
import com.ssafy.matchup_statistics.global.dto.response.LeagueInfoResponseDto;
import lombok.Data;

@Data
public class SummonerLeagueInfoResponseDto {
    private SummonerInfoResponseDto summonerInfoDto;
    private LeagueInfoResponseDto leagueInfoDto;

    public SummonerLeagueInfoResponseDto(SummonerInfoResponseDto summonerInfoDto, LeagueInfoResponseDto leagueInfoDto) {
        this.summonerInfoDto = summonerInfoDto;
        this.leagueInfoDto = leagueInfoDto;
    }
}
