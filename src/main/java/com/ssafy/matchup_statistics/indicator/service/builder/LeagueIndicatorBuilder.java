package com.ssafy.matchup_statistics.indicator.service.builder;

import com.ssafy.matchup_statistics.indicator.entity.riot.league.LeagueIndicator;
import com.ssafy.matchup_statistics.global.api.LeagueRestApi;
import com.ssafy.matchup_statistics.league.dto.response.LeagueInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * League Rest API를 통해 리그정보를 받아온 후, 리그 지표를 생성하는 빌더
 */
@Component
@RequiredArgsConstructor
public class LeagueIndicatorBuilder {

    @Autowired
    LeagueRestApi leagueRestApi;

    public LeagueIndicator build(String id) {
        LeagueInfoResponseDto leagueInfo = leagueRestApi.getLeagueInfoResponseDtoBySummonerId(id);
        return new LeagueIndicator(leagueInfo);
    }
}
