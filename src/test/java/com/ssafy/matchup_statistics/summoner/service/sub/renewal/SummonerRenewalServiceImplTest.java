package com.ssafy.matchup_statistics.summoner.service.sub.renewal;

import com.ssafy.matchup_statistics.global.api.flux.RiotWebClientFactory;
import com.ssafy.matchup_statistics.indicator.entity.Indicator;
import com.ssafy.matchup_statistics.indicator.entity.match.MatchIndicator;
import com.ssafy.matchup_statistics.indicator.service.builder.IndicatorFluxBuilder;
import com.ssafy.matchup_statistics.summoner.dao.SummonerDao;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class SummonerRenewalServiceImplTest {
    SummonerRenewalService target;

    @Mock
    SummonerDao summonerDao;

    @Mock
    RiotWebClientFactory riotWebClientFactory;

    @Mock
    IndicatorFluxBuilder indicatorFluxBuilder;

    void init() {
        MockitoAnnotations.openMocks(this);
        target = new SummonerRenewalServiceImpl(summonerDao, riotWebClientFactory, indicatorFluxBuilder);
    }

    @Test
    @DisplayName("갱신할 값이 1개일때")
    void renew1() {
        // given
        String summonerId = "test";
        List<MatchIndicator> matchIndicatorsBefore = new ArrayList<>();
        matchIndicatorsBefore.add(MatchIndicator.builder()
                .matchId("matchInDB").build());

        Indicator indicatorInDB = new Indicator(summonerId, matchIndicatorsBefore);
        given(summonerDao.getIndicatorInDB(summonerId)).willReturn(indicatorInDB);

        // when

        // then
    }

    @Test
    @DisplayName("갱신할 값이 20개일때")
    void renew20() {
        // given

        // when

        // then
    }

    @Test
    @DisplayName("갱신할 값이 101개일때")
    void renew101() {
        // given

        // when

        // then
    }
}