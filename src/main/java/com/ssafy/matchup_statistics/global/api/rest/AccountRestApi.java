package com.ssafy.matchup_statistics.global.api.rest;

import com.ssafy.matchup_statistics.global.dto.response.AccountResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class AccountRestApi {

    private final RestTemplate restTemplate;

    public AccountResponseDto getAccountResponseDto(String puuid) {
        String url = "https://asia.api.riotgames.com/riot/account/v1/accounts/by-puuid/" + puuid;
        return restTemplate.getForObject(url, AccountResponseDto.class);
    }

    public AccountResponseDto getAccountResponseDto(String gameName, String tagLine) {
        String url = "https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/"
                + gameName + "/" + tagLine;
        return restTemplate.getForObject(url, AccountResponseDto.class);
    }
}
