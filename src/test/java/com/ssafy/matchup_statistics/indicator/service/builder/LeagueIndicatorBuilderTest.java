package com.ssafy.matchup_statistics.indicator.service.builder;

import com.ssafy.matchup_statistics.indicator.entity.riot.league.LeagueIndicator;
import com.ssafy.matchup_statistics.global.api.LeagueRestApi;
import com.ssafy.matchup_statistics.league.dto.response.LeagueInfoResponseDto;
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
        LinkedHashMap<String, Object> leagueInfoHashMap = new LinkedHashMap<>();
        leagueInfoHashMap.put("tier", "CHALLENGER");
        leagueInfoHashMap.put("rank", "I");
        leagueInfoHashMap.put("wins", 50);
        leagueInfoHashMap.put("losses", 80);
        String id = "Hide on bush";
        LeagueInfoResponseDto leagueInfo = new LeagueInfoResponseDto(leagueInfoHashMap);
        given(leagueRestApi.getLeagueInfoResponseDtoBySummonerId(id)).willReturn(leagueInfo);

        //when
        LeagueIndicator leagueIndicator = target.build(id);

        //then
        assertThat(leagueIndicator.getTier()).isEqualTo("CHALLENGER");
        assertThat(leagueIndicator.getRank()).isEqualTo("I");
        assertThat(leagueIndicator.getWins()).isEqualTo(50);
        assertThat(leagueIndicator.getLosses()).isEqualTo(80);
    }

}