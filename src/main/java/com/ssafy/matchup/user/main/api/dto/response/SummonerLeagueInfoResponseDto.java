package com.ssafy.matchup.user.main.api.dto.response;

import com.ssafy.matchup.user.main.api.dto.LeagueInfoDto;
import com.ssafy.matchup.user.main.api.dto.SummonerInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SummonerLeagueInfoResponseDto {
    private SummonerInfoDto summonerInfoDto;
    private LeagueInfoDto leagueInfoDto;

}
