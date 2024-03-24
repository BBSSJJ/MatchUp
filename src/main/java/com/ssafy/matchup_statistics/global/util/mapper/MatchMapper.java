package com.ssafy.matchup_statistics.global.util.mapper;


import com.ssafy.matchup_statistics.global.dto.response.MatchDetailResponseDto;
import com.ssafy.matchup_statistics.match.entity.MatchDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MatchMapper {
    MatchDetail matchDetailResponseDtoToMatchDetail(MatchDetailResponseDto matchDetailResponseDto);

}
