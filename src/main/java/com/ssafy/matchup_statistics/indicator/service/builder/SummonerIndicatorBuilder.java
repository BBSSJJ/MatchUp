package com.ssafy.matchup_statistics.indicator.service.builder;

import com.ssafy.matchup_statistics.indicator.entity.SummonerIndicator;
import com.ssafy.matchup_statistics.indicator.entity.riot.RiotIndicator;
import com.ssafy.matchup_statistics.indicator.service.builder.riot.RiotIndicatorBuilder;
import com.ssafy.matchup_statistics.summoner.api.SummonerRestApi;
import com.ssafy.matchup_statistics.summoner.api.dto.response.SummonerInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SummonerIndicatorBuilder {

    private final SummonerRestApi summonerRestApi;
    private final RiotIndicatorBuilder riotIndicatorBuilder;

    // TODO 소환사명으로부터 라이엇 지표, 유저 지표 생성하기
    public SummonerIndicator makeNewSummonerIndicatorBySummonerName(String summonerName) {

        // 소환사명으로부터 소환사정보 조회
        SummonerInfoResponseDto summonerInfo = summonerRestApi.getSummonerInfoResponseDtoByName(summonerName);

        // 소환사정보로부터 라이엇 지표 생성
        RiotIndicator riotIndicator = riotIndicatorBuilder.build(summonerInfo);

        // 소환사정보로부터 유저 지표 생성
        // TODO user의 userInfoDto 조회(user server)
        // TODO userInfoDto로부터 UserIndicator(유저 지표) 생성
        // TODO LeagueIndicator, MacroIndicator, EtcIndicator, LandIndicator, UserIndicator로부터 SummonerIndicator(소환사 지표) 생성

        return null;
//        return new SummonerIndicator(riotIndicator, userIndicator);
    }
}
