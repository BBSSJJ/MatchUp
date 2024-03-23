package com.ssafy.matchup_statistics.match.service.mapper;


import com.ssafy.matchup_statistics.global.dto.response.MatchDetailResponseDto;
import com.ssafy.matchup_statistics.match.entity.MatchDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MatchDetailMapper {
    MatchDetail matchDetailResponseDtoToMatchDetail(MatchDetailResponseDto matchDetailResponseDto);

}
