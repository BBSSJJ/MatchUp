package com.ssafy.matchup.user.main.api.flux;

import com.ssafy.matchup.user.main.api.dto.response.SummonerLeagueAccountInfoResponseDto;
import com.ssafy.matchup.user.main.dto.request.RegistDumpUserRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
public class WebClientFactory {

    @Value("${ip.server.statistics}")
    String statisticsServer;

    private final WebClient webClient;

    public Mono<SummonerLeagueAccountInfoResponseDto> postByNameAndTag(String name, String tag) {
        URI uri = UriComponentsBuilder
//                .fromUriString("https://matchup.site" +
                .fromUriString("http://" + statisticsServer + ":9004" +
                        "/api/statistics/users/riot-ids/" + name + "/tag-lines/" + tag + "/regist")
                .encode()
                .build()
                .toUri();
        return webClient.post()
                .uri(uri)
                .retrieve()
                .bodyToMono(SummonerLeagueAccountInfoResponseDto.class);
    }

    public Mono<SummonerLeagueAccountInfoResponseDto> postById(String riotId) {
        URI uri = UriComponentsBuilder
//                .fromUriString("https://matchup.site" +
                .fromUriString("http://" + statisticsServer + ":9004" +
                        "/api/statistics/users/" + riotId)
                .encode()
                .build()
                .toUri();
        return webClient.post()
                .uri(uri)
                .retrieve()
                .bodyToMono(SummonerLeagueAccountInfoResponseDto.class);
    }


    public List<SummonerLeagueAccountInfoResponseDto> getDumpRiotAccount(int page, RegistDumpUserRequestDto registDumpUserRequestDto) {
        URI uri = UriComponentsBuilder
                .fromUriString("http://" + statisticsServer + ":9004" +
                        "/api/statistics/users/pages/" + page)
//                .fromUriString("https://" + "matchup.site" + "/api/summoners/leagues/accounts/league-entries/")
                .encode()
                .build()
                .toUri();

        Flux<SummonerLeagueAccountInfoResponseDto> responseDtoFlux = webClient.post()
                .uri(uri)
                .bodyValue(registDumpUserRequestDto)
                .retrieve()
                .bodyToFlux(SummonerLeagueAccountInfoResponseDto.class);
//                .take(20);

        return responseDtoFlux.collectList().block();
    }

//    public Mono<MessageDto> sendSummonerName(String name, String tag) {
//        URI uri = UriComponentsBuilder
//                .fromUriString("http://" + statisticsServer +
//                        ":9004/api/summoners/leagues/indicators/matches/riot-ids/" +
//                        name + "/tag-lines/" + tag + "/flux")
////                .fromUriString("https://matchup.site/api/summoners/leagues/indicators/matches/riot-ids/" +
////                        name + "/tag-lines/" + tag + "/flux")
//                .encode()
//                .build()
//                .toUri();
//
//        return webClient.post()
//                .uri(uri)
//                .retrieve()
//                .bodyToMono(MessageDto.class);
//    }

}

