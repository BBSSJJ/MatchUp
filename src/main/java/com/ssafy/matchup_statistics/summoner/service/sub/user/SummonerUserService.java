package com.ssafy.matchup_statistics.summoner.service.sub.user;

import com.ssafy.matchup_statistics.global.dto.response.SummonerInfoResponseDto;

public interface SummonerUserService {
    SummonerInfoResponseDto registSummoner(String gameName, String tagLine);


    SummonerInfoResponseDto loginSummoner(String SummonerId);
}
