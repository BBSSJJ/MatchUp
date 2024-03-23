package com.ssafy.matchup_statistics.indicator.service.builder;

import com.ssafy.matchup_statistics.global.dto.response.MatchDetailResponseDto;
import com.ssafy.matchup_statistics.global.dto.response.MatchTimelineResponseDto;
import com.ssafy.matchup_statistics.indicator.entity.match.MatchIndicator;
import com.ssafy.matchup_statistics.global.api.MatchRestApi;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
/**
 * Match Rest API를 통해 매치정보를 받아온 후, 매치 지표를 생성하는 빌더
 * 생성된 매치지표에는 라인지표와 운영지표가 포함됨
 */
public class MatchIndicatorBuilder {

    @Autowired
    MatchRestApi matchRestApi;

    public List<MatchIndicator> buildMatches(String puuid) {
        List<String> matchesResponseDtoByPuuid = matchRestApi.getMatchesResponseDtoByPuuid(puuid);
        List<MatchIndicator> matchIndicators = new ArrayList<>();

        // 20개 매치에 대해 각각 세부정보 조회
        matchesResponseDtoByPuuid.forEach(matchId -> {
            MatchDetailResponseDto matchDetailResponseDtoByMatchId = matchRestApi.getMatchDetailResponseDtoByMatchId(matchId);
            MatchTimelineResponseDto matchTimelineResponseDtoByMatchId = matchRestApi.getMatchTimelineResponseDtoByMatchId(matchId);

            // 세부정보를 통해 매치 지표 생성
            MatchIndicator matchIndicator = new MatchIndicator(puuid, matchDetailResponseDtoByMatchId, matchTimelineResponseDtoByMatchId);
            matchIndicators.add(matchIndicator);
        });
        return matchIndicators;
    }
}
