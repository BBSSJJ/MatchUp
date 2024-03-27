package com.ssafy.matchup_statistics.global.api.rest;

import com.ssafy.matchup_statistics.global.dto.response.AccountResponseDto;
import com.ssafy.matchup_statistics.global.dto.response.LeagueInfoResponseDto;
import com.ssafy.matchup_statistics.global.dto.response.MatchDetailResponseDto;
import com.ssafy.matchup_statistics.global.dto.response.MatchTimelineResponseDto;
import com.ssafy.matchup_statistics.global.dto.response.SummonerInfoResponseDto;
import com.ssafy.matchup_statistics.league.dto.request.LeagueEntryRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RiotRestApiAdaptor {

    private final AccountRestApi accountRestApi;
    private final SummonerRestApi summonerRestApi;
    private final LeagueRestApi leagueRestApi;
    private final MatchRestApi matchRestApi;

    public SummonerInfoResponseDto getSummonerInfoBySummonerName(String summonerName) {
        return summonerRestApi.getSummonerInfoResponseDtoBySummonerName(summonerName);
    }

    public SummonerInfoResponseDto getSummonerInfo(String gameName, String tagLine) {
        AccountResponseDto accountInfo = accountRestApi.getAccountResponseDto(gameName, tagLine);
        return summonerRestApi.getSummonerInfoResponseDtoByPuuid(accountInfo.getPuuid());
    }

    public SummonerInfoResponseDto getSummonerInfo(String puuid) {
        return summonerRestApi.getSummonerInfoResponseDtoByPuuid(puuid);
    }

    public List<String> getMatchIdsByPuuid(String puuid) {
        return matchRestApi.getMatchesResponseDtoByPuuid(puuid);
    }

    public MatchDetailResponseDto getMatchDetailResponseDtoByMatchId(String matchId) {
        return matchRestApi.getMatchDetailResponseDtoByMatchId(matchId);
    }

    public MatchTimelineResponseDto getMatchTimelineResponseDtoByMatchId(String matchId) {
        return matchRestApi.getMatchTimelineResponseDtoByMatchId(matchId);
    }

    public List<LeagueInfoResponseDto> getLeagueInfoResponseByTier(int page, LeagueEntryRequestDto dto) {
        return leagueRestApi.getLeagueInfoResponseByTier(page, dto);
    }

    public LeagueInfoResponseDto getLeagueInfoResponse(String id) {
        return leagueRestApi.getLeagueInfoResponseBySummonerId(id);
    }

    public AccountResponseDto getAccountInfo(String gameName, String tagLine) {
        return accountRestApi.getAccountResponseDto(gameName, tagLine);
    }

    public AccountResponseDto getAccountInfo(String puuid) {
        return accountRestApi.getAccountResponseDto(puuid);
    }
}
