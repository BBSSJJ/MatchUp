package com.ssafy.matchup_statistics.global.util.mapper;


import com.ssafy.matchup_statistics.global.dto.response.SummonerInfoResponseDto;
import com.ssafy.matchup_statistics.summoner.entity.SummonerDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SummonerMapper {
    SummonerDetail summonerInfoResponseDtoToSummonerDetail(SummonerInfoResponseDto summonerInfoResponseDto);

}
