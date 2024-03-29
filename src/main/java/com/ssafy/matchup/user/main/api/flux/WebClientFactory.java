package com.ssafy.matchup.user.main.api.flux;

import com.ssafy.matchup.global.dto.MessageDto;
import com.ssafy.matchup.user.main.api.dto.response.SummonerLeagueInfoResponseDto;
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

    public List<SummonerLeagueInfoResponseDto> getDumpRiotAccount(int page, RegistDumpUserRequestDto registDumpUserRequestDto) {
        URI uri = UriComponentsBuilder
                .fromUriString("http://" + statisticsServer + ":9004/api/summoners/leagues/accounts/league-entries/")
//                .fromUriString("https://" + "matchup.site" + "/api/summoners/leagues/accounts/league-entries/")
                .path(String.valueOf(page))
                .encode()
                .build()
                .toUri();

        Flux<SummonerLeagueInfoResponseDto> responseDtoFlux = webClient.post()
                .uri(uri)
                .bodyValue(registDumpUserRequestDto)
                .retrieve()
                .bodyToFlux(SummonerLeagueInfoResponseDto.class)
                .take(20);

        return responseDtoFlux.collectList().block();
    }

    public Mono<MessageDto> sendSummonerName(String name, String tag){
        URI uri = UriComponentsBuilder
                .fromUriString("http://" + statisticsServer +
                        ":9004/api/summoners/leagues/indicators/matches/riot-ids/" +
                        name + "/tag-lines/" + tag + "/flux")
                .encode()
                .build()
                .toUri();

        return webClient.post()
                .uri(uri)
                .retrieve()
                .bodyToMono(MessageDto.class);
    }

}

