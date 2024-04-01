package com.ssafy.matchup_statistics.summoner.dao;

import com.ssafy.matchup_statistics.indicator.entity.Indicator;
import com.ssafy.matchup_statistics.match.entity.Match;
import com.ssafy.matchup_statistics.summoner.entity.Summoner;

public interface SummonerDao {
    Summoner getSummonerInDB(String gameName, String tagLine);

    Indicator getIndicatorInDB(String SummonerId);

    Match getMatchInDB(String matchId);


}
