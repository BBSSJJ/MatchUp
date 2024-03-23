package com.ssafy.matchup_statistics.global.util.mapper;

import com.ssafy.matchup_statistics.global.dto.response.LeagueInfoResponseDto;
import com.ssafy.matchup_statistics.league.entity.League;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LeagueMapper {
    League leagueInfoResponseDtoToLeague(LeagueInfoResponseDto leagueInfoResponseDto);
}
