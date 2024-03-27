package com.ssafy.matchup_statistics;

import com.ssafy.matchup_statistics.global.api.rest.AccountRestApi;
import com.ssafy.matchup_statistics.global.api.rest.LeagueRestApi;
import com.ssafy.matchup_statistics.global.dto.response.LeagueInfoResponseDto;
import com.ssafy.matchup_statistics.global.api.rest.MatchRestApi;
import com.ssafy.matchup_statistics.global.dto.response.MatchDetailResponseDto;
import com.ssafy.matchup_statistics.global.dto.response.MatchTimelineResponseDto;
import com.ssafy.matchup_statistics.global.api.rest.SummonerRestApi;
import com.ssafy.matchup_statistics.global.dto.response.SummonerInfoResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class RiotRestApiTest {

    @Autowired
    SummonerRestApi summonerRestApi;
    @Autowired
    MatchRestApi matchRestApi;
    @Autowired
    LeagueRestApi leagueRestApi;
    @Autowired
    AccountRestApi accountRestApi;

    @Test
    @DisplayName("puuid 조회 후 20개 요청 불러오는지 테스트")
    void getTwentyRequestTest(){
        //given
        String summonerName = "보던가";

        //when : puuid, matches, league 받아오는지 검증
        SummonerInfoResponseDto summonerInfoResponseDto = summonerRestApi.getSummonerInfoResponseDtoBySummonerName(summonerName);
        List<String> matches =  matchRestApi.getMatchesResponseDtoByPuuid(summonerInfoResponseDto.getPuuid());
        LeagueInfoResponseDto leagueInfo = leagueRestApi.getLeagueInfoResponseBySummonerId(summonerInfoResponseDto.getId());
        // 응답 로깅
        log.info("summoner info : {}", summonerInfoResponseDto);
        log.info("league info : {}",leagueInfo);
        matches.stream().forEach(match -> {log.info("match id : {}", match);});
        
        //then
        // Summoner Id 검증
        assertEquals(matches.size(), 20);
        // League 검증
        assertEquals(leagueInfo.getSummonerName(), summonerName);
        // Match 검증
        matches.stream().forEach(matchId -> {
            MatchDetailResponseDto matchDetail = matchRestApi.getMatchDetailResponseDtoByMatchId(matchId);
            assertEquals(matchId, matchDetail.getMetadata().getMatchId());
//            log.info("매치 유저 확인 : {}", matchDetail.getInfoDto());
        });
        // MatchDetail 확인
        matches.stream().forEach(matchId -> {
            MatchTimelineResponseDto matchTimelineDto = matchRestApi.getMatchTimelineResponseDtoByMatchId(matchId);
            assertThat(matchTimelineDto
                    .getInfo()
                    .getFrames()
                    .get(0)
                    .getParticipantFrames()._1.currentGold).isEqualTo(500);
        });
        MatchTimelineResponseDto cur = matchRestApi.getMatchTimelineResponseDtoByMatchId(matches.get(0));
//        log.info("매치 타임라인 확인 : {}", cur);
    }

}