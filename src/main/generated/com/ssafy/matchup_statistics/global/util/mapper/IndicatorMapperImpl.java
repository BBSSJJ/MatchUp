package com.ssafy.matchup_statistics.global.util.mapper;

import com.ssafy.matchup_statistics.indicator.dto.response.IndicatorResponseDto;
import com.ssafy.matchup_statistics.indicator.entity.Indicator;
import com.ssafy.matchup_statistics.indicator.entity.match.MatchIndicator;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-27T11:54:03+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class IndicatorMapperImpl implements IndicatorMapper {

    @Override
    public IndicatorResponseDto indicatorToIndicatorResponseDto(Indicator indicator) {
        if ( indicator == null ) {
            return null;
        }

        IndicatorResponseDto indicatorResponseDto = new IndicatorResponseDto();

        indicatorResponseDto.setId( indicator.getId() );
        indicatorResponseDto.setSummonerId( indicator.getSummonerId() );
        List<MatchIndicator> list = indicator.getMatchIndicators();
        if ( list != null ) {
            indicatorResponseDto.setMatchIndicators( new ArrayList<MatchIndicator>( list ) );
        }
        indicatorResponseDto.setMatchIndicatorStatistics( indicator.getMatchIndicatorStatistics() );

        return indicatorResponseDto;
    }
}
