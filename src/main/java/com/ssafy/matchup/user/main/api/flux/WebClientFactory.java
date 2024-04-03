package com.ssafy.matchup.user.main.api.flux;

import com.ssafy.matchup.user.main.api.dto.response.AccountResponseDto;
import com.ssafy.matchup.user.main.api.dto.response.SummonerLeagueAccountInfoResponseDto;
import com.ssafy.matchup.user.main.dto.request.RegistDumpUserRequestDto;
import com.ssafy.matchup.user.main.dto.response.RsoResponse;
import io.netty.handler.logging.LogLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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

        HttpClient httpClient = HttpClient.create().wiretap("reactor.netty.http.client.HttpClient",
                LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL);

        String authStr = userName + ":" + password;
        String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());
        log.info("basic auth credential : {}", base64Creds);


        webClient.mutate()
                .defaultHeader("Content-Type", "application/x-www-form-urlencoded")
                .defaultHeader("X-Riot-Token", riotApiKey)
                .defaultHeader("Authorization", "Basic " + base64Creds)
                .defaultHeader("Accept", "*/*")
                .defaultHeader("Accept-Encoding", "gzip, deflate, br")
                .defaultHeader("Connection", "keep-alive")
                .clientConnector(new ReactorClientHttpConnector(httpClient));

        URI uri = UriComponentsBuilder
                .fromUriString("https://auth.riotgames.com/token")
                .encode()
                .build()
                .toUri();

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("code", riotCode);
        formData.add("redirect_uri", redirectUri);

        log.info("riot api key : {}", riotApiKey);
        log.info("Content-Type : {}", webClient.head().header("Content-Type"));
        log.info("X-Riot-Token : {}", webClient.head().header("X-Riot-Token"));
        log.info("Authorization : {}", webClient.head().header("Authorization"));
        log.info("form 1 : {}, {}", "grant_type", formData.get("grant_type"));
        log.info("form 2 : {}, {}", "code", formData.get("code"));
        log.info("form 3 : {}, {}", "redirect_uri", formData.get("redirect_uri"));

        return webClient.post()
                .uri(uri)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> {
                    log.info("response : {}", response);
                    return Mono.error(new IllegalStateException(("Failed!")));
                })
                .bodyToMono(RsoResponse.class)
                .doOnError(exception -> log.warn("Failed to send notification to {}, cause {} | exception body : {}", uri, exception.getMessage(), exception.getStackTrace()));
    }

    public Mono<AccountResponseDto> getAccountResponseDtoByToken(String tokenType, String accessToken) {
        webClient.mutate()
                .defaultHeader("Authorization", tokenType + " " + accessToken)
                .defaultHeader("X-Riot-Token", riotApiKey);

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

