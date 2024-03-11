package com.ssafy.matchup_statistics.summoner.controller;

import com.ssafy.matchup_statistics.summoner.api.dto.response.SummonerInfoResponseDto;
import com.ssafy.matchup_statistics.summoner.entity.Summoner;
import com.ssafy.matchup_statistics.summoner.service.SummonerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SummonerController {

    private final SummonerService summonerService;
    @GetMapping("/test/{name}")
    public ResponseEntity<Summoner> getSummonerInfo(@PathVariable(value = "name") String name) {
        return ResponseEntity.ok(summonerService.getSummonerDto(name));
    }
}
