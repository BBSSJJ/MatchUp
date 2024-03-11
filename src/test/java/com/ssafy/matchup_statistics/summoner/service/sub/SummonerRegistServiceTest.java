package com.ssafy.matchup_statistics.summoner.service.sub;

import com.ssafy.matchup_statistics.summoner.api.MatchRestApi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SummonerRegistServiceTest {

    @InjectMocks
    SummonerRegistService target;

    @Mock
    MatchRestApi matchRestApi;

    @Test
    @DisplayName("Riot Api 통해 최근 20개 경기 불러오는지 확인")
    public void getTwentyMatchesTest() {
        //given

        //when
//        target.getRecentTwentyMatches();

        //then
    }

}