package com.ssafy.matchup_statistics.global.util.mapper;

import com.ssafy.matchup_statistics.indicator.dto.response.IndicatorResponseDto;
import com.ssafy.matchup_statistics.indicator.entity.Indicator;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface IndicatorMapper {
    IndicatorResponseDto indicatorToIndicatorResponseDto(Indicator indicator);

}
