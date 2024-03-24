package com.ssafy.matchup_statistics.global.util;

import com.ssafy.matchup_statistics.indicator.entity.Indicator;
import com.ssafy.matchup_statistics.match.entity.Match;
import com.ssafy.matchup_statistics.summoner.entity.Summoner;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MongoTemplateAdaptor {
    private final MongoTemplate mongoTemplate;

    public Indicator findIndicator(String id) {
        return mongoTemplate.findOne(
                new Query(Criteria.where("_id").is(id)),
                Indicator.class, "indicators");
    }

    public Summoner findSummonerInfo(String id) {
        return mongoTemplate.findOne(
                new Query(Criteria.where("_id").is(id)),
                Summoner.class, "indicators");
    }

    public void saveIndicator(Indicator indicator) {
        mongoTemplate.save(indicator);
    }

    public void saveMatch(Match match) {
        mongoTemplate.save(match);
    }

    public void saveSummoner(Summoner summoner) {
        mongoTemplate.save(summoner);
    }
}
