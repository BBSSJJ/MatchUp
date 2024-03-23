package com.ssafy.matchup_statistics.summoner.service;

import com.ssafy.matchup_statistics.league.dto.request.LeagueEntryRequestDto;
import com.ssafy.matchup_statistics.summoner.dto.response.SummonerLeagueInfoResponseDto;
import com.ssafy.matchup_statistics.summoner.service.sub.SummonerLeagueInfoService;
import com.ssafy.matchup_statistics.summoner.service.sub.SummonerTotalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SummonerService {

    private final SummonerLeagueInfoService summonerLeagueInfoService;
    private final SummonerTotalService summonerTotalService;

    public SummonerLeagueInfoResponseDto getSummonerLeagueInfo(String gameName, String tagLine) {
        return summonerLeagueInfoService.getSummonerInfoWithLeagueInfo(gameName, tagLine);
    }

    public SummonerLeagueInfoResponseDto getSummonerLeagueInfo(String puuid) {
        return summonerLeagueInfoService.getSummonerInfoWithLeagueInfo(puuid);
    }

    public SummonerLeagueInfoResponseDto getSummonerLeagueInfoBySummonerName(String summonerName) {
        return summonerLeagueInfoService.getSummonerInfoWithLeagueInfoBySummonerName(summonerName);
    }

    public void saveSummonerLeagueIndicatorMatches(String gameName, String tagLine) {
        summonerTotalService.save(gameName, tagLine);
    }

    public int saveAllSummonerLeagueIndicatorMatches(Integer pages, LeagueEntryRequestDto dto) {
        return summonerTotalService.saveLeagueEntry(pages, dto);
    }
}
