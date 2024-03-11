package com.ssafy.matchup_statistics.summoner.service.mapper;

import com.ssafy.matchup_statistics.summoner.api.dto.response.LeagueInfoResponseDto;
import com.ssafy.matchup_statistics.summoner.api.dto.response.SummonerInfoResponseDto;
import com.ssafy.matchup_statistics.summoner.entity.League;
import com.ssafy.matchup_statistics.summoner.entity.Summoner;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface SummonerMapper {

    Summoner summonerInfoResponseDtoToSummoner(SummonerInfoResponseDto summonerInfoResponseDto);
    League leagueInfoResponseDtoToLeague(LeagueInfoResponseDto leagueInfoResponseDto);
}
