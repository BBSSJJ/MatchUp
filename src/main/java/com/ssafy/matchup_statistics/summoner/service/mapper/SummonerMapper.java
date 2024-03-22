package com.ssafy.matchup_statistics.summoner.service.mapper;

import com.ssafy.matchup_statistics.global.dto.response.SummonerInfoResponseDto;
import com.ssafy.matchup_statistics.summoner.entity.Summoner;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SummonerMapper {

    Summoner summonerInfoResponseDtoToSummoner(SummonerInfoResponseDto summonerInfoResponseDto);
//    League leagueInfoResponseDtoToLeague(LeagueInfoResponseDto leagueInfoResponseDto);
}
