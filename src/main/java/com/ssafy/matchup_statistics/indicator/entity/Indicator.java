package com.ssafy.matchup_statistics.indicator.entity;

import com.ssafy.matchup_statistics.indicator.entity.match.MatchIndicatorStatistics;
import com.ssafy.matchup_statistics.indicator.entity.match.MatchIndicator;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "indicators")
public class Indicator {
    @Id
    private ObjectId id;
    private String summonerId;
    private List<MatchIndicator> matchIndicators;
    private MatchIndicatorStatistics matchIndicatorStatistics;

    public Indicator(String summonerId,
                     List<MatchIndicator> matchIndicators) {
        this.summonerId = summonerId;
        this.matchIndicators = matchIndicators;
    }
}
