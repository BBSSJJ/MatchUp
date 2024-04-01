package com.ssafy.matchup_statistics.summoner.service.sub.record;

import com.ssafy.matchup_statistics.summoner.dto.response.SummonerRecordInfoResponseDto;

public interface SummonerRecordService {

    SummonerRecordInfoResponseDto getSummonerRecordInfo(String gameName, String tagLine);

    SummonerRecordInfoResponseDto getSummonerRecordInfo(long userId);

    void saveSummonerRecordInfo(Long userId);

    void saveSummonerRecordInfo(String gameName, String tagLine);
}
