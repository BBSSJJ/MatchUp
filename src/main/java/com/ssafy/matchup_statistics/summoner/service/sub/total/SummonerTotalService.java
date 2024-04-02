package com.ssafy.matchup_statistics.summoner.service.sub.total;

import com.ssafy.matchup_statistics.league.dto.request.LeagueEntryRequestDto;
import com.ssafy.matchup_statistics.summoner.dto.response.SummonerLeagueAccountInfoResponseDto;

import java.util.List;

public interface SummonerTotalService {
    int saveLeagueEntry(LeagueEntryRequestDto dto);

    void save(String gameName, String tagLine);

    int saveLeagueEntry(String tier);

    List<SummonerLeagueAccountInfoResponseDto> getSummonerLeagueAccountInfo(Integer page, LeagueEntryRequestDto dto);
}
