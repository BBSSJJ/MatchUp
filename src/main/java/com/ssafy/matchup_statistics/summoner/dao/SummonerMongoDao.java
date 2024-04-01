package com.ssafy.matchup_statistics.summoner.dao;

import com.ssafy.matchup_statistics.global.exception.RiotApiError;
import com.ssafy.matchup_statistics.global.exception.RiotApiException;
import com.ssafy.matchup_statistics.global.exception.RiotDataError;
import com.ssafy.matchup_statistics.global.exception.RiotDataException;
import com.ssafy.matchup_statistics.indicator.entity.Indicator;
import com.ssafy.matchup_statistics.match.entity.Match;
import com.ssafy.matchup_statistics.summoner.entity.Summoner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
@Slf4j
public class SummonerMongoDao implements SummonerDao {

    private final MongoTemplate mongoTemplate;

    @Override
    public Summoner getSummonerInDB(String gameName, String tagLine) {
        Query query = Query.query(
                Criteria.where("account.gameName").regex(Pattern.compile(gameName, Pattern.CASE_INSENSITIVE))
                        .and("account.tagLine").regex(Pattern.compile(tagLine, Pattern.CASE_INSENSITIVE)));

        Summoner summoners = mongoTemplate.findOne(query, Summoner.class, "summoners");

        if (summoners == null) throw new RiotDataException(RiotDataError.NOT_IN_STATISTICS_DATABASE);
        else log.info("summoner founded : {}", summoners.getId());

        return summoners;
    }

    @Override
    public Indicator getIndicatorInDB(String SummonerId) {
        Query query = Query.query(
                Criteria.where("_id").is(SummonerId));

        Indicator indicators = mongoTemplate.findOne(query, Indicator.class, "indicators");

        if (indicators == null) throw new RiotDataException(RiotDataError.NOT_IN_STATISTICS_DATABASE);
        else log.info("indicator founded : {}", indicators.getId());

        return indicators;
    }

    @Override
    public Match getMatchInDB(String matchId) {
        Query query = Query.query(
                Criteria.where("_id").is(matchId));

        Match matches = mongoTemplate.findOne(query, Match.class, "matches");

        if (matches == null) throw new RiotDataException(RiotDataError.NOT_IN_STATISTICS_DATABASE);
        else log.info("match founded : {}", matches.getId());

        return matches;
    }

}
