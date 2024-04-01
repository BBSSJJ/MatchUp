package com.ssafy.matchup_statistics.indicator.dto.response;

import com.ssafy.matchup_statistics.indicator.entity.Indicator;
import com.ssafy.matchup_statistics.indicator.entity.match.MatchIndicator;
import com.ssafy.matchup_statistics.indicator.entity.match.MatchIndicatorStatistics;
import lombok.Data;
import org.bson.types.ObjectId;

import java.util.List;

@Data
public class IndicatorResponseDto {
    private String id;
    private List<MatchIndicator> matchIndicators;
    private MatchIndicatorStatistics matchIndicatorStatistics;

    public IndicatorResponseDto(Indicator indicator) {
        this.id = indicator.getId();
        this.matchIndicators = indicator.getMatchIndicators();
        this.matchIndicatorStatistics = indicator.getMatchIndicatorStatistics();
    }
}
