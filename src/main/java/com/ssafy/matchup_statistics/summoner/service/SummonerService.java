package com.ssafy.matchup_statistics.summoner.service;

import com.ssafy.matchup_statistics.league.dto.request.LeagueEntryRequestDto;
import com.ssafy.matchup_statistics.summoner.dto.response.SummonerDetailInfoResponseDto;
import com.ssafy.matchup_statistics.summoner.dto.response.SummonerLeagueAccountInfoResponseDto;
import com.ssafy.matchup_statistics.summoner.dto.response.SummonerLeagueInfoResponseDto;
import com.ssafy.matchup_statistics.summoner.dto.response.SummonerRecordInfoResponseDto;
import com.ssafy.matchup_statistics.summoner.service.sub.SummonerLeagueInfoService;
import com.ssafy.matchup_statistics.summoner.service.sub.detail.SummonerDetailService;
import com.ssafy.matchup_statistics.summoner.service.sub.record.SummonerRecordService;
import com.ssafy.matchup_statistics.summoner.service.sub.total.SummonerTotalService;
import com.ssafy.matchup_statistics.summoner.service.sub.user.SummonerUserService;
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

    private final SummonerRecordService summonerRecordService;

    private final SummonerDetailService summonerDetailService;

    private final SummonerUserService summonerUserService;

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

    public int saveAllSummonerLeagueIndicatorMatchesFlux(LeagueEntryRequestDto dto) {
        return summonerTotalFluxService.saveLeagueEntry(dto);
    }

    public void saveSummonerLeagueIndicatorMatchesRest(String gameName, String tagLine) {
        summonerTotalRestService.save(gameName, tagLine);
    }

    public int saveAllSummonerLeagueIndicatorMatchesRest(Integer pages, LeagueEntryRequestDto dto) {
        return summonerTotalRestService.saveLeagueEntry(dto);
    }

    public List<SummonerLeagueAccountInfoResponseDto> getSummonerLeagueInfo(Integer page, LeagueEntryRequestDto dto) {
        return summonerLeagueInfoService.getSummonerLeagueInfo(page, dto);
    }

    public int saveHighTierSummonerLeagueIndicatorMatchesFlux(String tier) {
        return summonerTotalFluxService.saveLeagueEntry(tier);
    }

    public SummonerRecordInfoResponseDto getSummonerRecordInfo(String gameName, String tagLine) {
        return summonerRecordService.getSummonerRecordInfo(gameName, tagLine);
    }

    public SummonerRecordInfoResponseDto getSummonerRecordInfo(Long userId) {
        return summonerRecordService.getSummonerRecordInfo(userId);
    }

    public SummonerDetailInfoResponseDto getSummonerDetailInfo(String gameName, String tagLine) {
        return summonerDetailService.getSummonerDetailInfo(gameName, tagLine);
    }

    public SummonerDetailInfoResponseDto getSummonerDetailInfo(Long userId) {
        return summonerDetailService.getSummonerDetailInfo(userId);
    }

    public void saveSummonerRecordInfo(Long userId) {
        summonerRecordService.saveSummonerRecordInfo(userId);
    }

    public void saveSummonerRecordInfo(String gameName, String tagLine) {
        summonerRecordService.saveSummonerRecordInfo(gameName, tagLine);
    }

    public SummonerLeagueAccountInfoResponseDto registSummoner(String gameName, String tagLine) {
        return summonerUserService.registSummoner(gameName, tagLine);
    }

    public SummonerLeagueAccountInfoResponseDto loginSummoner(Long userId) {
        return summonerUserService.loginSummoner(userId);
    }

    public List<SummonerLeagueAccountInfoResponseDto> getSummonerLeagueAccountInfo(Integer page, LeagueEntryRequestDto dto) {
        return summonerTotalFluxService.getSummonerLeagueAccountInfo(page, dto);
    }
}
