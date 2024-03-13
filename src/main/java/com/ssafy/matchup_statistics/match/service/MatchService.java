package com.ssafy.matchup_statistics.match.service;

import com.ssafy.matchup_statistics.Indicator.entity.SummonerIndicator;
import com.ssafy.matchup_statistics.match.api.MatchRestApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {
    private MatchRestApi matchRestApi;
    public SummonerIndicator produceStatistics(String puuid) {
        // TODO 라이엇 서버로부터 최근 20경기 id 받아오기
        List<String> recentMatches = matchRestApi.getTwentyMatchesResponseDtoByPuuid(puuid);

        // TODO 20경기 각각의 요청을 보내기

        // TODO 각 요청마다 통계 분석해서 엔티티로 만들기
        // TODO 생성된 엔티티 저장하기
        return null;
    }
}
