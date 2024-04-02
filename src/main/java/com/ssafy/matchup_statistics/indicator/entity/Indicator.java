package com.ssafy.matchup_statistics.indicator.entity;

import com.ssafy.matchup_statistics.indicator.entity.match.MatchIndicatorStatistics;
import com.ssafy.matchup_statistics.indicator.entity.match.MatchIndicator;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "indicators")
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Slf4j
public class Indicator {
    @Id
    private String id;
    private List<MatchIndicator> matchIndicators;
    private MatchIndicatorStatistics matchIndicatorStatistics;

    public Indicator(String summonerId,
                     List<MatchIndicator> matchIndicators) {
        this.id = summonerId;
        this.matchIndicators = matchIndicators;
        log.debug("생성된 매치정보 : {}", matchIndicators);
        this.matchIndicatorStatistics = new MatchIndicatorStatistics(matchIndicators);
    }

    public Indicator(String summonerId) {
        this.id = summonerId;
        this.matchIndicators = new ArrayList<>();
        this.matchIndicatorStatistics = new MatchIndicatorStatistics();
    }

    public void renew(Indicator matchIdsToRenew) {
        matchIndicators.addAll(matchIdsToRenew.getMatchIndicators());
        this.matchIndicatorStatistics = new MatchIndicatorStatistics(matchIndicators);
    }
}
