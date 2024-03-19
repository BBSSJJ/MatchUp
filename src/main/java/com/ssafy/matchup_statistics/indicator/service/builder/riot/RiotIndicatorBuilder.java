package com.ssafy.matchup_statistics.indicator.service.builder.riot;

import com.ssafy.matchup_statistics.indicator.entity.riot.league.LeagueIndicator;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.MatchIndicator;
import com.ssafy.matchup_statistics.indicator.entity.riot.RiotIndicator;
import com.ssafy.matchup_statistics.indicator.service.builder.riot.league.LeagueIndicatorBuilder;
import com.ssafy.matchup_statistics.indicator.service.builder.riot.match.MatchIndicatorBuilder;
import com.ssafy.matchup_statistics.league.api.LeagueRestApi;
import com.ssafy.matchup_statistics.match.api.MatchRestApi;
import com.ssafy.matchup_statistics.summoner.api.dto.response.SummonerInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RiotIndicatorBuilder {

    private final LeagueRestApi leagueRestApi;
    private final MatchRestApi matchRestApi;
    private final LeagueIndicatorBuilder leagueIndicatorBuilder;
    private final MatchIndicatorBuilder matchIndicatorBuilder;


    // TODO 소환사명으로부터 라이엇 지표 생성하기
    public RiotIndicator build(SummonerInfoResponseDto summonerInfo) {

        // 소환사정보로부터 리그정보 조회 및 리그지표 생성
        // TODO summoner의 leagueDto 조회(riot api) -> 기능테스트 작성
        LinkedHashMap<String, Object> leagueInfo = leagueRestApi.getLeagueResponseDtoById(summonerInfo.getId());
        LeagueIndicator leagueIndicator = leagueIndicatorBuilder.build(leagueInfo);

        // 소환사정보로부터 매치정보 조회 및 매치지표 생성
        List<String> matches = matchRestApi.getMatchesResponseDtoByPuuid(summonerInfo.getPuuid());
        MatchIndicator matchIndicator = matchIndicatorBuilder.build(summonerInfo.getPuuid(), matches);

        return new RiotIndicator(leagueIndicator, matchIndicator);
    }
}
