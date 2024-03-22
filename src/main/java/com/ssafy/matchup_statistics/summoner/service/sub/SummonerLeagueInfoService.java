package com.ssafy.matchup_statistics.summoner.service.sub;

import com.ssafy.matchup_statistics.account.api.AccountRestApi;
import com.ssafy.matchup_statistics.league.api.LeagueRestApi;
import com.ssafy.matchup_statistics.league.dto.response.LeagueInfoResponseDto;
import com.ssafy.matchup_statistics.summoner.api.SummonerRestApi;
import com.ssafy.matchup_statistics.account.dto.response.AccountResponseDto;
import com.ssafy.matchup_statistics.summoner.dto.response.SummonerInfoResponseDto;
import com.ssafy.matchup_statistics.summoner.dto.response.SummonerLeagueInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SummonerLeagueInfoService {

    private final AccountRestApi accountRestApi;
    private final SummonerRestApi summonerRestApi;
    private final LeagueRestApi leagueRestApi;

    public SummonerLeagueInfoResponseDto createNewSummonerInfoWithLeagueInfo(String puuid) {
        AccountResponseDto accountDto = accountRestApi.getAccountResponseDto(puuid);
        SummonerInfoResponseDto summonerInfoDto = summonerRestApi.getLeagueInfoResponseDtoByPuuid(accountDto.getPuuid());
        LeagueInfoResponseDto leagueInfoDto = leagueRestApi.getLeagueInfoResponseDtoBySummonerId(summonerInfoDto.getId());
        return new SummonerLeagueInfoResponseDto(summonerInfoDto, leagueInfoDto);
    }

    public SummonerLeagueInfoResponseDto createNewSummonerInfoWithLeagueInfo(String gameName, String tagLine) {
        AccountResponseDto accountDto = accountRestApi.getAccountResponseDto(gameName, tagLine);
        SummonerInfoResponseDto summonerInfoDto = summonerRestApi.getLeagueInfoResponseDtoByPuuid(accountDto.getPuuid());
        LeagueInfoResponseDto leagueInfoDto = leagueRestApi.getLeagueInfoResponseDtoBySummonerId(summonerInfoDto.getId());
        return new SummonerLeagueInfoResponseDto(summonerInfoDto, leagueInfoDto);
    }

    public SummonerLeagueInfoResponseDto createNewSummonerInfoWithLeagueInfoBySummonerName(String summonerName) {
        SummonerInfoResponseDto summonerInfoDto = summonerRestApi.getSummonerInfoResponseDtoBySummonerName(summonerName);
        LeagueInfoResponseDto leagueInfoDto = leagueRestApi.getLeagueInfoResponseDtoBySummonerId(summonerInfoDto.getId());
        return new SummonerLeagueInfoResponseDto(summonerInfoDto, leagueInfoDto);
    }
}
