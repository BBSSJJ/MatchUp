package com.ssafy.matchup_statistics.summoner.service.sub.record;

import com.ssafy.matchup_statistics.global.dao.UserDao;
import com.ssafy.matchup_statistics.global.dto.response.SummonerInfoResponseDto;
import com.ssafy.matchup_statistics.indicator.entity.Indicator;
import com.ssafy.matchup_statistics.match.entity.Match;
import com.ssafy.matchup_statistics.summoner.dto.response.SummonerRecordInfoResponseDto;
import com.ssafy.matchup_statistics.summoner.entity.Summoner;
import com.ssafy.matchup_statistics.summoner.service.builder.SummonerBuilder;
import com.ssafy.matchup_statistics.summoner.dao.SummonerDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

class SummonerRecordServiceTest {

    public SummonerRecordService target;

    @Mock
    public SummonerBuilder summonerBuilder;

    @Mock
    public UserDao userDao;

    @Mock
    public SummonerDao summonerDao;

    @BeforeEach
    void init(){
        MockitoAnnotations.initMocks(this);
        target = new SummonerRecordServiceImpl(summonerDao, userDao, summonerBuilder);
    }

    @Test
    @DisplayName("유저정보 DB에 있는지 확인 후, 없으면 라이엇 API를 통해 받아오는지 테스트")
    void getSummonerRecordInfoTest() {
        // given
        String inDBId = "id1";
        List<String> matchIds = new ArrayList<>();
        matchIds.add(inDBId);
        Summoner summonerInDB = Summoner.builder()
                .id(inDBId)
                .matchIds(matchIds)
                .build();
        given(summonerDao.getSummonerInDB("name1", "tag1")).willReturn(summonerInDB);
        given(summonerDao.getIndicatorInDB("id1")).willReturn(new Indicator());
        given(summonerDao.getMatchInDB("matchId1")).willReturn(new Match());

        String notInDBId = "id2";
        SummonerInfoResponseDto summonerInfoResponseDto = new SummonerInfoResponseDto();
        summonerInfoResponseDto.setId(notInDBId);
        SummonerRecordInfoResponseDto summonerNotInDB = new SummonerRecordInfoResponseDto();
        summonerNotInDB.setSummonerInfo(summonerInfoResponseDto);

        given(summonerDao.getSummonerInDB("name2", "tag2")).willReturn(null);
        given(summonerBuilder.buildRecord("name2", "tag2")).willReturn(summonerNotInDB);

        // when
        SummonerRecordInfoResponseDto summonerInDBResponse = target.getSummonerRecordInfo("name1", "tag1");
        SummonerRecordInfoResponseDto summonerNotInDBResponse = target.getSummonerRecordInfo("name2", "tag2");

        // then
        assertThat(summonerInDBResponse.getSummonerInfo().getId()).isEqualTo(inDBId);
        assertThat(summonerNotInDBResponse.getSummonerInfo().getId()).isEqualTo(notInDBId);
    }
}