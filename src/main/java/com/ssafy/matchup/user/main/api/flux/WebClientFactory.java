package com.ssafy.matchup.user.main.api.flux;

import com.ssafy.matchup.user.main.api.dto.response.AccountResponseDto;
import com.ssafy.matchup.user.main.api.dto.response.SummonerLeagueAccountInfoResponseDto;
import com.ssafy.matchup.user.main.dto.request.RegistDumpUserRequestDto;
import com.ssafy.matchup.user.main.dto.response.OauthResponse;
import io.netty.handler.logging.LogLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

import java.net.URI;
import java.util.Base64;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebClientFactory {

    private final WebClient webClient;
    @Value("${ip.server.statistics}")
    String statisticsServer;
    @Value("${RIOT_API_KEY}")
    String riotApiKey;
    @Value("${RIOT_CLIENT_ID}")
    String userName;
//    @Value("${RIOT_CLIENT_SECRET}")
    String password = "XFi9j3GzogWPAwZBQvCBZPJEguhP8yZ9milz8dt3rAj";
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

    public Mono<OauthResponse> getRiotAccountByRiotCode(String riotCode) {

        HttpClient httpClient = HttpClient.create().wiretap("reactor.netty.http.client.HttpClient",
                LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL);

        String authStr = userName + ":" + password;
        String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());

        WebClient client = WebClient.builder()
                .defaultHeader("Content-Type", "application/x-www-form-urlencoded")
                .defaultHeader("X-Riot-Token", riotApiKey)
                .defaultHeader("Authorization", "Basic " + base64Creds)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

        URI uri = UriComponentsBuilder
                .fromUriString("https://auth.riotgames.com/token")
                .encode()
                .build()
                .toUri();

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("code", riotCode);
        formData.add("redirect_uri", redirectUri);

        return client.post()
                .uri(uri)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> {
                    log.info("response : {}", response);
                    return Mono.error(new IllegalStateException(("Failed!")));
                })
                .bodyToMono(OauthResponse.class)
                .doOnError(exception -> log.warn("Failed to send notification to {}, cause {} | exception body : {}", uri, exception.getMessage(), exception.getStackTrace()));
    }

    public Mono<AccountResponseDto> getAccountResponseDtoByToken(String tokenType, String accessToken) {
        WebClient client = WebClient.builder()
                .defaultHeader("Content-Type", "application/x-www-form-urlencoded")
                .defaultHeader("Authorization", tokenType + " " + accessToken)
                .build();

        URI uri = UriComponentsBuilder
                .fromUriString("https://asia.api.riotgames.com/riot/account/v1/accounts/me")
                .encode()
                .build()
                .toUri();

        return client.get()
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

