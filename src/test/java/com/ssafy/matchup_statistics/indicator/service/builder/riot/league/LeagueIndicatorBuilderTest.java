package com.ssafy.matchup_statistics.indicator.service.builder.riot.league;

import com.ssafy.matchup_statistics.indicator.entity.riot.league.LeagueIndicator;
import com.ssafy.matchup_statistics.indicator.service.builder.riot.league.LeagueIndicatorBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.LinkedHashMap;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = LeagueIndicatorBuilder.class)
class LeagueIndicatorBuilderTest {

    @Autowired
    LeagueIndicatorBuilder target;

    @Test
    @DisplayName("리그 정보로부터 리그 지표를 잘 생성하는지 테스트")
    void buildLeagueIndicatorTest(){
        //given
        LinkedHashMap<String, Object> leagueInfo = new LinkedHashMap<>();
        String tier = "CHALLENGER";
        String rank = "I";
        Integer wins = 50;
        Integer losses = 80;

        leagueInfo.put("tier", tier);
        leagueInfo.put("rank", rank);
        leagueInfo.put("wins", wins);
        leagueInfo.put("losses", losses);
        //when
        LeagueIndicator leagueIndicator = target.build(leagueInfo);

        //then
        Assertions.assertEquals(leagueIndicator.getTier(), tier);
        Assertions.assertEquals(leagueIndicator.getRank(), rank);
        Assertions.assertEquals(leagueIndicator.getWins(), wins);
        Assertions.assertEquals(leagueIndicator.getLosses(), losses);
    }

}