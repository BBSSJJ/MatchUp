package com.ssafy.matchup_statistics.summoner.service;

import com.ssafy.matchup_statistics.summoner.api.dto.response.SummonerInfoResponseDto;
import com.ssafy.matchup_statistics.summoner.entity.Summoner;
import com.ssafy.matchup_statistics.summoner.service.mapper.SummonerMapper;
import com.ssafy.matchup_statistics.summoner.service.sub.SummonerRegistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SummonerService {
    private final SummonerRegistService summonerRegistService;

    public Summoner getSummonerDto(String name){
        summonerRegistService.registSummonerInfo(name);
        return summonerRegistService.getSummonerByName(name);
    }
}
