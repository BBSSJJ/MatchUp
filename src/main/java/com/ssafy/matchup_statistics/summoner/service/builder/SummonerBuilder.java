package com.ssafy.matchup_statistics.summoner.service.builder;

import com.ssafy.matchup_statistics.summoner.dto.response.SummonerRecordInfoResponseDto;
import com.ssafy.matchup_statistics.summoner.entity.Summoner;

public interface SummonerBuilder {
    SummonerRecordInfoResponseDto buildRecord(String gameName, String tagLine);

    Summoner build(String gameName, String tagLine);

    Summoner build(String summonerId);

    void buildAsync(String gameName, String tagLine);
}
