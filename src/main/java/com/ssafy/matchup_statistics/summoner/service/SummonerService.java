package com.ssafy.matchup_statistics.summoner.service;

import com.ssafy.matchup_statistics.league.dto.request.LeagueEntryRequestDto;
import com.ssafy.matchup_statistics.summoner.dto.response.SummonerLeagueAccountInfoResponseDto;
import com.ssafy.matchup_statistics.summoner.dto.response.SummonerLeagueInfoResponseDto;
import com.ssafy.matchup_statistics.summoner.service.sub.SummonerLeagueInfoService;
import com.ssafy.matchup_statistics.summoner.service.sub.SummonerTotalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SummonerService {

    private final SummonerLeagueInfoService summonerLeagueInfoService;

    @Qualifier("summonerTotalFluxService")
    private final SummonerTotalService summonerTotalFluxService;

    @Qualifier("summonerTotalRestService")
    private final SummonerTotalService summonerTotalRestService;

    public SummonerLeagueInfoResponseDto getSummonerLeagueInfo(String gameName, String tagLine) {
        return summonerLeagueInfoService.getSummonerInfoWithLeagueInfo(gameName, tagLine);
    }

    public SummonerLeagueInfoResponseDto getSummonerLeagueInfo(String puuid) {
        return summonerLeagueInfoService.getSummonerInfoWithLeagueInfo(puuid);
    }

    public SummonerLeagueInfoResponseDto getSummonerLeagueInfoBySummonerName(String summonerName) {
        return summonerLeagueInfoService.getSummonerInfoWithLeagueInfoBySummonerName(summonerName);
    }

    public void saveSummonerLeagueIndicatorMatchesFlux(String gameName, String tagLine) {
        summonerTotalFluxService.save(gameName, tagLine);
    }

    public int saveAllSummonerLeagueIndicatorMatchesFlux(Integer pages, LeagueEntryRequestDto dto) {
        return summonerTotalFluxService.saveLeagueEntry(pages, dto);
    }

    public void saveSummonerLeagueIndicatorMatchesRest(String gameName, String tagLine) {
        summonerTotalRestService.save(gameName, tagLine);
    }

    public int saveAllSummonerLeagueIndicatorMatchesRest(Integer pages, LeagueEntryRequestDto dto) {
        return summonerTotalRestService.saveLeagueEntry(pages, dto);
    }

    public List<SummonerLeagueAccountInfoResponseDto> getSummonerLeagueInfo(Integer page, LeagueEntryRequestDto dto) {
        return summonerLeagueInfoService.getSummonerLeagueInfo(page, dto);
    }
}
