package com.ssafy.matchup.user.main.api.flux;

import com.ssafy.matchup.user.main.api.dto.response.AccountResponseDto;
import com.ssafy.matchup.user.main.api.dto.response.SummonerLeagueAccountInfoResponseDto;
import com.ssafy.matchup.user.main.dto.request.RegistDumpUserRequestDto;
import com.ssafy.matchup.user.main.dto.response.RsoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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

    private final WebClient webClient;
    @Value("${ip.server.statistics}")
    String statisticsServer;
    @Value("${RIOT_CLIENT_ID}")
    String userName;
    @Value("${RIOT_CLIENT_SECRET}")
    String password;
    @Value("${RIOT_REDIRECT_URI}")
    String redirectUri;

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
                        "/api/statistics/users/summoners/" + riotId + "/login")
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

    public Mono<RsoResponse> getRiotAccountByRiotCode(String riotCode) {
        webClient.mutate()
                .defaultHeader("Content-Type", "application/x-www-form-urlencoded")
                .defaultHeaders(header -> header.setBasicAuth(userName, password));

        URI uri = UriComponentsBuilder
                .fromUriString("https://auth.riotgames.com/token")
                .encode()
                .build()
                .toUri();

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("code", riotCode);
        formData.add("redirect_uri", redirectUri);

        return webClient.post()
                .uri(uri)
                .bodyValue(formData)
                .retrieve().bodyToMono(RsoResponse.class);
    }

    public Mono<AccountResponseDto> getAccountResponseDtoByToken(String tokenType, String accessToken) {
        webClient.mutate()
                .defaultHeader("Authorization", tokenType + " " + accessToken);

        URI uri = UriComponentsBuilder
                .fromUriString("https://asia.api.riotgames.com/riot/account/v1/accounts/me")
                .encode()
                .build()
                .toUri();

        return webClient.get()
                .uri(uri)
                .retrieve().bodyToMono(AccountResponseDto.class);
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

