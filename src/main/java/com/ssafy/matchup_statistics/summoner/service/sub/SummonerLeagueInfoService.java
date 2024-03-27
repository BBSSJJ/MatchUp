package com.ssafy.matchup_statistics.summoner.service.sub;

import com.ssafy.matchup_statistics.global.api.rest.RiotRestApiAdaptor;
import com.ssafy.matchup_statistics.global.dto.response.LeagueInfoResponseDto;
import com.ssafy.matchup_statistics.global.dto.response.SummonerInfoResponseDto;
import com.ssafy.matchup_statistics.summoner.dto.response.SummonerLeagueInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SummonerLeagueInfoService {

    private final RiotRestApiAdaptor riotRestApiAdaptor;

    public SummonerLeagueInfoResponseDto getSummonerInfoWithLeagueInfo(String puuid) {
        SummonerInfoResponseDto summonerInfoDto = riotRestApiAdaptor.getSummonerInfo(puuid);
        LeagueInfoResponseDto leagueInfoDto = riotRestApiAdaptor.getLeagueInfoResponse(summonerInfoDto.getId());
        return new SummonerLeagueInfoResponseDto(summonerInfoDto, leagueInfoDto);
    }

    public SummonerLeagueInfoResponseDto getSummonerInfoWithLeagueInfo(String gameName, String tagLine) {
        SummonerInfoResponseDto summonerInfoDto = riotRestApiAdaptor.getSummonerInfo(gameName, tagLine);
        LeagueInfoResponseDto leagueInfoDto = riotRestApiAdaptor.getLeagueInfoResponse(summonerInfoDto.getId());
        return new SummonerLeagueInfoResponseDto(summonerInfoDto, leagueInfoDto);
    }

    public SummonerLeagueInfoResponseDto getSummonerInfoWithLeagueInfoBySummonerName(String summonerName) {
        SummonerInfoResponseDto summonerInfoDto = riotRestApiAdaptor.getSummonerInfoBySummonerName(summonerName);
        LeagueInfoResponseDto leagueInfoDto = riotRestApiAdaptor.getLeagueInfoResponse(summonerInfoDto.getId());
        return new SummonerLeagueInfoResponseDto(summonerInfoDto, leagueInfoDto);
    }
}
