package com.ssafy.matchup_statistics.summoner.controller;

import com.ssafy.matchup_statistics.indicator.entity.SummonerIndicator;
import com.ssafy.matchup_statistics.summoner.api.dto.response.SummonerInfoResponseDto;
import com.ssafy.matchup_statistics.summoner.entity.Summoner;
import com.ssafy.matchup_statistics.summoner.service.SummonerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SummonerController {

    private final SummonerService summonerService;
    @GetMapping("/test/{name}")
    public ResponseEntity<SummonerIndicator> getSummonerInfo(@PathVariable(value = "name") String name) {
        SummonerIndicator newSummonerIndicator = summonerService.getNewSummonerIndicator(name);
        return ResponseEntity.ok(newSummonerIndicator);
    }
}
