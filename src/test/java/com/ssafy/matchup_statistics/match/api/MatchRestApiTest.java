package com.ssafy.matchup_statistics.match.api;

import com.ssafy.matchup_statistics.match.api.dto.response.MatchDetailResponseDto;
import com.ssafy.matchup_statistics.summoner.api.SummonerRestApi;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class MatchRestApiTest {
    @Autowired
    MatchRestApi matchRestApi;

    @Autowired
    SummonerRestApi summonerRestApi;

    private String username;
    private String puuid;
    private List<String> matches;
    @Test
    @DisplayName("puuid 조회 후 20개 요청 불러오는지 테스트")
    void getTwentyRequestTest(){
        //given
        username = "Hide on bush";

        //when
        puuid = summonerRestApi.getSummonerInfoResponseDtoByName(username).getPuuid();
        matches =  matchRestApi.getTwentyMatchesResponseDtoByPuuid(puuid);

        //then
        matches.stream().forEach(match -> {log.info("match id : {}", match);});
        assertEquals(matches.size(), 20);

        matches.stream().forEach(match -> {
            MatchDetailResponseDto matchDetail = matchRestApi.getMatchDetailResponseDtoByMatchId(match);

            assertEquals(match, matchDetail.getMetadataDto().getMatchId());
            log.info("매치 유저 확인 : {}", matchDetail.getInfoDto());
        });

    }

}