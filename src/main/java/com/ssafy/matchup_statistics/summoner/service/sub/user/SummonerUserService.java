package com.ssafy.matchup_statistics.summoner.service.sub.user;

import com.ssafy.matchup_statistics.summoner.dto.response.SummonerLeagueAccountInfoResponseDto;

public interface SummonerUserService {
    SummonerLeagueAccountInfoResponseDto registSummoner(String gameName, String tagLine);

    SummonerLeagueAccountInfoResponseDto loginSummoner(Long userId);

    SummonerLeagueAccountInfoResponseDto loginSummoner(String gameName, String tagLine);

    SummonerLeagueAccountInfoResponseDto loginSummoner(String summonerId);
}
