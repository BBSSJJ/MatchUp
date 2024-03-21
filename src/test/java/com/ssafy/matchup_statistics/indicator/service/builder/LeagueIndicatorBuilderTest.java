package com.ssafy.matchup_statistics.indicator.service.builder;

import com.ssafy.matchup_statistics.indicator.entity.riot.league.LeagueIndicator;
import com.ssafy.matchup_statistics.indicator.service.builder.LeagueIndicatorBuilder;
import com.ssafy.matchup_statistics.league.api.LeagueRestApi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedHashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class LeagueIndicatorBuilderTest {

    @InjectMocks
    LeagueIndicatorBuilder target = new LeagueIndicatorBuilder();

    @Mock
    LeagueRestApi leagueRestApi;

    @Test
    @DisplayName("리그 정보로부터 리그 지표를 잘 생성하는지 테스트")
    void buildLeagueIndicatorTest() {
        //given
        LinkedHashMap<String, Object> leagueInfo = new LinkedHashMap<>();
        leagueInfo.put("tier", "CHALLENGER");
        leagueInfo.put("rank", "I");
        leagueInfo.put("wins", 50);
        leagueInfo.put("losses", 80);
        String id = "Hide on bush";
        given(leagueRestApi.getLeagueResponseDtoById(id)).willReturn(leagueInfo);

        //when
        LeagueIndicator leagueIndicator = target.build(id);

        //then
        assertThat(leagueIndicator.getTier()).isEqualTo("CHALLENGER");
        assertThat(leagueIndicator.getRank()).isEqualTo("I");
        assertThat(leagueIndicator.getWins()).isEqualTo(50);
        assertThat(leagueIndicator.getLosses()).isEqualTo(80);
    }

}