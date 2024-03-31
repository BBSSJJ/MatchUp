package com.ssafy.matchup_statistics.summoner.service.sub;

import com.ssafy.matchup_statistics.league.dto.request.LeagueEntryRequestDto;

public interface SummonerTotalService {
    int saveLeagueEntry(LeagueEntryRequestDto dto);

    void save(String gameName, String tagLine);

    int saveLeagueEntry(String tier);
}
