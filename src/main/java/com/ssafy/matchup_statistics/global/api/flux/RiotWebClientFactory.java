package com.ssafy.matchup_statistics.global.api.flux;

import com.ssafy.matchup_statistics.global.dto.HighTierResponseDto;
import com.ssafy.matchup_statistics.global.dto.response.*;
import com.ssafy.matchup_statistics.global.exception.RiotApiError;
import com.ssafy.matchup_statistics.global.exception.RiotApiException;
import com.ssafy.matchup_statistics.league.dto.request.LeagueEntryRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class RiotWebClientFactory {

    @Autowired
    private final WebClient webClient;

    public Flux<LeagueInfoResponseDto> getLeagueInfoResponseByTier(int page, LeagueEntryRequestDto dto) {
        URI uri = UriComponentsBuilder
                .fromUriString("https://kr.api.riotgames.com/lol/league/v4/entries")
                .path(dto.getLeagueEntryRequestUrl())
                .queryParam("page", page)
                .encode()
                .build()
                .toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(LeagueInfoResponseDto.class);
    }

    public Mono<HighTierResponseDto> getHighTierResponseByTier(String highTier) {
        String tierPath;
        switch (highTier) {
            case "CHALLENGER": {
                tierPath = "challengerleagues";
                break;
            }
            case "GRANDMASTER": {
                tierPath = "grandmasterleagues";
                break;
            }
            case "MASTER": {
                tierPath = "masterleagues";
                break;
            }
            default:
                throw new RiotApiException(RiotApiError.NO_LEAGUE_ENTRY_ERROR);
        }

        URI uri = UriComponentsBuilder
                .fromUriString("https://kr.api.riotgames.com/lol/league/v4/")
                .path(tierPath)
                .path("/by-queue/RANKED_SOLO_5x5")
                .encode()
                .build()
                .toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(HighTierResponseDto.class);
    }

    public Flux<LeagueInfoResponseDto> getLeagueInfoResponseBySummonerId(String id) {
        URI uri = UriComponentsBuilder
                .fromUriString("https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/")
                .path(id)
                .encode()
                .build()
                .toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(LeagueInfoResponseDto.class);
    }

    public Flux<String> getMatchesResponseDtoByPuuid(String puuid) {
        URI uri = UriComponentsBuilder
                .fromUriString("https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/")
                .path(puuid + "/ids")
                .queryParam("start", 0)
                .queryParam("count", 20)
                .queryParam("type", "ranked")
                .encode()
                .build()
                .toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(List.class)
                .flatMapIterable(list -> list);
    }

    public Mono<SummonerInfoResponseDto> getSummonerInfoResponseDtoBySummonerName(String summonerName) {
        URI uri = UriComponentsBuilder
                .fromUriString("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/")
                .path(summonerName)
                .encode()
                .build()
                .toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(SummonerInfoResponseDto.class);
    }

    public Mono<SummonerInfoResponseDto> getSummonerInfoResponseDtoBySummonerId(String summonerId) {
        URI uri = UriComponentsBuilder
                .fromUriString("https://kr.api.riotgames.com/lol/summoner/v4/summoners/")
                .path(summonerId)
                .encode()
                .build()
                .toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(SummonerInfoResponseDto.class);
    }

    public Mono<SummonerInfoResponseDto> getSummonerInfoResponseDtoByPuuid(String puuid) {
        URI uri = UriComponentsBuilder
                .fromUriString("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-puuid/")
                .path(puuid)
                .encode()
                .build()
                .toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(SummonerInfoResponseDto.class);
    }

    public Mono<AccountResponseDto> getAccountInfo(String puuid) {
        URI uri = UriComponentsBuilder
                .fromUriString("https://asia.api.riotgames.com/riot/account/v1/accounts/by-puuid/")
                .path(puuid)
                .encode()
                .build()
                .toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(AccountResponseDto.class);
    }

    public Mono<AccountResponseDto> getAccountInfo(String gameName, String tagLine) {
        URI uri = UriComponentsBuilder
                .fromUriString("https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/")
                .path(gameName)
                .path("/")
                .path(tagLine)
                .encode()
                .build()
                .toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(AccountResponseDto.class);
    }

    public Mono<MatchDetailResponseDto> getMatchDetailResponseDtoByMatchId(String matchId) {
        URI uri = UriComponentsBuilder
                .fromUriString("https://asia.api.riotgames.com/lol/match/v5/matches/")
                .path(matchId)
                .encode()
                .build()
                .toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(MatchDetailResponseDto.class);
    }

    public Mono<MatchTimelineResponseDto> getMatchTimelineResponseDtoByMatchId(String matchId) {
        URI uri = UriComponentsBuilder
                .fromUriString("https://asia.api.riotgames.com/lol/match/v5/matches/")
                .path(matchId + "/timeline")
                .encode()
                .build()
                .toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(MatchTimelineResponseDto.class);
    }
}
