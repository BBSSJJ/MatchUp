package com.ssafy.matchup.user.main.api.flux;

import com.ssafy.matchup.user.main.api.dto.response.AccountResponseDto;
import com.ssafy.matchup.user.main.api.dto.response.SummonerLeagueAccountInfoResponseDto;
import com.ssafy.matchup.user.main.dto.request.RegistDumpUserRequestDto;
import com.ssafy.matchup.user.main.dto.response.RsoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.HttpClientTransport;
import org.eclipse.jetty.client.Request;
import org.eclipse.jetty.http.HttpField;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.reactive.JettyClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

        SslContextFactory.Client sslContextFactory = new SslContextFactory.Client();
        HttpClient httpClient = new HttpClient((HttpClientTransport) sslContextFactory) {
            @Override
            public Request newRequest(URI uri) {
                Request request = super.newRequest(uri);
                return enhance(request);
            }
        };

        String authStr = userName + ":" + password;
        String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());

        webClient.mutate()
                .defaultHeader("Content-Type", "application/x-www-form-urlencoded")
                .defaultHeader("X-Riot-Token", riotApiKey)
                .defaultHeader("Authorization", "Basic " + base64Creds)
                .clientConnector(new JettyClientHttpConnector(httpClient));

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
                .retrieve().bodyToMono(RsoResponse.class)
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

    private Request enhance(Request request) {
        StringBuilder group = new StringBuilder();
        request.onRequestBegin(theRequest -> {
            // append request url and method to group
        });
        request.onRequestHeaders(theRequest -> {
            for (HttpField header : theRequest.getHeaders()) {
                // append request headers to group
            }
        });
        request.onRequestContent((theRequest, content) -> {
            // append content to group
        });
        request.onRequestSuccess(theRequest -> {
            log.debug(group.toString());
            group.delete(0, group.length());
        });
        group.append("\n");
        request.onResponseBegin(theResponse -> {
            // append response status to group
        });
        request.onResponseHeaders(theResponse -> {
            for (HttpField header : theResponse.getHeaders()) {
                // append response headers to group
            }
        });
        request.onResponseContent((theResponse, content) -> {
            // append content to group
        });
        request.onResponseSuccess(theResponse -> {
            log.debug(group.toString());
        });
        return request;
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

