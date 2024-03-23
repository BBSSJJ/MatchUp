package com.ssafy.matchup_statistics.summoner.service.sub;

import com.ssafy.matchup_statistics.account.api.AccountRestApi;
import com.ssafy.matchup_statistics.global.api.LeagueRestApi;
import com.ssafy.matchup_statistics.global.api.RiotApiAdaptor;
import com.ssafy.matchup_statistics.global.dto.response.LeagueInfoResponseDto;
import com.ssafy.matchup_statistics.global.api.SummonerRestApi;
import com.ssafy.matchup_statistics.account.dto.response.AccountResponseDto;
import com.ssafy.matchup_statistics.global.dto.response.SummonerInfoResponseDto;
import com.ssafy.matchup_statistics.summoner.dto.response.SummonerLeagueInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SummonerLeagueInfoService {

    private final RiotApiAdaptor riotApiAdaptor;

    public SummonerLeagueInfoResponseDto getSummonerInfoWithLeagueInfo(String puuid) {
        SummonerInfoResponseDto summonerInfoDto = riotApiAdaptor.getSummonerInfo(puuid);
        LeagueInfoResponseDto leagueInfoDto = riotApiAdaptor.getLeagueInfoResponse(summonerInfoDto.getId());
        return new SummonerLeagueInfoResponseDto(summonerInfoDto, leagueInfoDto);
    }

    public SummonerLeagueInfoResponseDto getSummonerInfoWithLeagueInfo(String gameName, String tagLine) {
        SummonerInfoResponseDto summonerInfoDto = riotApiAdaptor.getSummonerInfo(gameName, tagLine);
        LeagueInfoResponseDto leagueInfoDto = riotApiAdaptor.getLeagueInfoResponse(summonerInfoDto.getId());
        return new SummonerLeagueInfoResponseDto(summonerInfoDto, leagueInfoDto);
    }

    public SummonerLeagueInfoResponseDto getSummonerInfoWithLeagueInfoBySummonerName(String summonerName) {
        SummonerInfoResponseDto summonerInfoDto = riotApiAdaptor.getSummonerInfoBySummonerName(summonerName);
        LeagueInfoResponseDto leagueInfoDto = riotApiAdaptor.getLeagueInfoResponse(summonerInfoDto.getId());
        return new SummonerLeagueInfoResponseDto(summonerInfoDto, leagueInfoDto);
    }
}
