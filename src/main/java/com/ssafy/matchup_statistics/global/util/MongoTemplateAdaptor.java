package com.ssafy.matchup_statistics.global.util;

import com.ssafy.matchup_statistics.indicator.entity.Indicator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MongoTemplateAdaptor {
    private final MongoTemplate mongoTemplate;

    public Indicator getSummonerIndicatorById (String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query, Indicator.class, "summoner_indicators");
    }

    public void saveSummonerIndicator(Indicator indicator) {
        mongoTemplate.save(indicator);
    }
}
