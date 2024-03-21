package com.ssafy.matchup_statistics.summoner.service;

import com.ssafy.matchup_statistics.indicator.entity.SummonerIndicator;
import com.ssafy.matchup_statistics.indicator.service.SummonerIndicatorService;
import com.ssafy.matchup_statistics.summoner.entity.Summoner;
import com.ssafy.matchup_statistics.summoner.service.sub.SummonerRegistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SummonerService {

    private final SummonerIndicatorService summonerIndicatorService;
    private final MongoTemplate mongoTemplate;

    public SummonerIndicator getNewSummonerIndicator(String name) {
        SummonerIndicator summonerIndicator = summonerIndicatorService.createSummonerIndicatorBySummonerName(name);
        mongoTemplate.save(summonerIndicator);
        return summonerIndicator;
    }

}
