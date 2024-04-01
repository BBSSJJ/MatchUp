package com.ssafy.matchup_statistics.summoner.service.sub.detail;

import com.ssafy.matchup_statistics.global.dao.UserDao;
import com.ssafy.matchup_statistics.global.dto.jpa.RiotAccountInfoDto;
import com.ssafy.matchup_statistics.global.dto.jpa.UserInfoDto;
import com.ssafy.matchup_statistics.global.dto.jpa.UserRiotAccountDto;
import com.ssafy.matchup_statistics.global.entity.RiotAccount;
import com.ssafy.matchup_statistics.global.entity.SummonerProfile;
import com.ssafy.matchup_statistics.indicator.entity.Indicator;
import com.ssafy.matchup_statistics.match.entity.Match;
import com.ssafy.matchup_statistics.summoner.dao.SummonerDao;
import com.ssafy.matchup_statistics.summoner.dto.response.SummonerDetailInfoResponseDto;
import com.ssafy.matchup_statistics.summoner.entity.Summoner;
import com.ssafy.matchup_statistics.summoner.service.builder.SummonerBuilder;
import com.ssafy.matchup_statistics.summoner.service.sub.record.SummonerRecordServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class SummonerDetailServiceImplTest {

    SummonerDetailService target;

    @Mock
    public UserDao userDao;

    @Mock
    public SummonerDao summonerDao;

    @BeforeEach
    void init(){
        MockitoAnnotations.initMocks(this);
        target = new SummonerDetailServiceImpl(summonerDao, userDao);
    }

    @Test
    @DisplayName("받아온 사용자 정보로 상세정보 잘 응답하는지 테스트")
    void SummonerDetailServiceImplTest() {
        // given
        Long id = 1L;
        RiotAccount riotAccount = RiotAccount.builder()
                .summonerProfile(SummonerProfile.builder()
                        .name("test")
                        .tag("kr1")
                        .build())
                .build();
        UserRiotAccountDto userRiotAccountDto = new UserRiotAccountDto(
                new UserInfoDto(1L),
                new RiotAccountInfoDto(riotAccount));
        given(userDao.getUserRiotAccountDto(id)).willReturn(userRiotAccountDto);

        String inDBId = "id1";
        Summoner summonerInDB = Summoner.builder()
                .id(inDBId)
                .build();
        given(summonerDao.getSummonerInDB("test", "kr1")).willReturn(summonerInDB);
        given(summonerDao.getIndicatorInDB("name1")).willReturn(new Indicator());

        // when
        SummonerDetailInfoResponseDto summonerDetailInfo = target.getSummonerDetailInfo(id);

        // then
        assertThat(summonerDetailInfo);
    }
}