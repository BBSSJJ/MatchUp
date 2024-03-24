package com.ssafy.matchup_statistics.indicator.dto.response;

import com.ssafy.matchup_statistics.indicator.entity.match.MatchIndicator;
import com.ssafy.matchup_statistics.indicator.entity.match.MatchIndicatorStatistics;
import lombok.Data;
import org.bson.types.ObjectId;

import java.util.List;

@Data
public class IndicatorResponseDto {
    private ObjectId id;
    private String summonerId;
    private List<MatchIndicator> matchIndicators;
    private MatchIndicatorStatistics matchIndicatorStatistics;
}
