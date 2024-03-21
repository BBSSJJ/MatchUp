package com.ssafy.matchup_statistics.indicator.service.builder;

import com.ssafy.matchup_statistics.indicator.entity.riot.match.MatchIndicator;
import com.ssafy.matchup_statistics.match.api.MatchRestApi;
import com.ssafy.matchup_statistics.match.api.dto.response.MatchDetailResponseDto;
import com.ssafy.matchup_statistics.match.api.dto.response.MatchTimelineResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
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
        matchesResponseDtoByPuuid.forEach(matchId -> {
            MatchDetailResponseDto matchDetailResponseDtoByMatchId = matchRestApi.getMatchDetailResponseDtoByMatchId(matchId);
            MatchTimelineResponseDto matchTimelineResponseDtoByMatchId = matchRestApi.getMatchTimelineResponseDtoByMatchId(matchId);
            MatchIndicator matchIndicator = new MatchIndicator(puuid, matchDetailResponseDtoByMatchId, matchTimelineResponseDtoByMatchId);
            matchIndicators.add(matchIndicator);
        });
        return matchIndicators;
    }
}
