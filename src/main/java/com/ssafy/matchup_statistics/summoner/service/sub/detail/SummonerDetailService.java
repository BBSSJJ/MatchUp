package com.ssafy.matchup_statistics.summoner.service.sub.detail;

import com.ssafy.matchup_statistics.summoner.dto.response.SummonerDetailInfoResponseDto;

public interface SummonerDetailService {

    SummonerDetailInfoResponseDto getSummonerDetailInfo(Long userId);

    SummonerDetailInfoResponseDto getSummonerDetailInfo(String gameName, String tagLine);
}
