package com.ssafy.matchup_statistics.summoner.service.sub.user;

import com.ssafy.matchup_statistics.global.dto.response.SummonerInfoResponseDto;
import com.ssafy.matchup_statistics.global.exception.RiotApiException;
import com.ssafy.matchup_statistics.summoner.dao.SummonerDao;
import com.ssafy.matchup_statistics.summoner.dto.response.SummonerRecordInfoResponseDto;
import com.ssafy.matchup_statistics.summoner.entity.Summoner;
import com.ssafy.matchup_statistics.summoner.service.builder.SummonerBuilder;
import com.ssafy.matchup_statistics.summoner.service.sub.renewal.SummonerRenewalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SummonerUserServiceImpl implements SummonerUserService{

    private final SummonerBuilder summonerBuilder;
    private final SummonerRenewalService summonerRenewalService;

    @Override
    public SummonerInfoResponseDto registSummoner(String gameName, String tagLine) {

        Summoner summoner = summonerBuilder.build(gameName, tagLine);

        return new SummonerInfoResponseDto(summoner);
    }

    @Override
    public SummonerInfoResponseDto loginSummoner(String summonerId) {

        // 소환사 id로 소환사 불러오기
        Summoner summoner = summonerBuilder.build(summonerId);

        // 로그인 시 갱신 1회 수행
        summonerRenewalService.renew(summoner);

        return new SummonerInfoResponseDto(summoner);
    }
}
