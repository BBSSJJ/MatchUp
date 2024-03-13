package com.ssafy.matchup_statistics.summoner.service.sub;

import com.ssafy.matchup_statistics.league.api.LeagueRestApi;
import com.ssafy.matchup_statistics.league.api.dto.response.LeagueInfoResponseDto;
import com.ssafy.matchup_statistics.summoner.api.dto.response.SummonerInfoResponseDto;
import com.ssafy.matchup_statistics.summoner.api.SummonerRestApi;
import com.ssafy.matchup_statistics.league.entity.League;
import com.ssafy.matchup_statistics.summoner.entity.Summoner;
import com.ssafy.matchup_statistics.summoner.service.mapper.SummonerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@RequiredArgsConstructor
public class SummonerRegistService {

    private final SummonerRestApi summonerRestApi;
    private final LeagueRestApi leagueRestApi;
    private final SummonerMapper summonerMapper;
    private final MongoTemplate mongoTemplate;

    public void registSummonerInfo(String name) {
        Summoner summonerByName = getSummonerByName(name);
//        League leagueBySummoner = getLeagueBySummoner(summonerByName);
        saveEntities(summonerByName);
    }

    public Summoner getSummonerByName(String name) {
        SummonerInfoResponseDto dto = summonerRestApi.getSummonerInfoResponseDtoByName(name);
        return summonerMapper.summonerInfoResponseDtoToSummoner(dto);
    }

    public League getLeagueBySummoner(Summoner summoner) {
        String id = summoner.getId();
        LeagueInfoResponseDto dto = leagueRestApi.getLeagueResponseDtoById(id);
        return summonerMapper.leagueInfoResponseDtoToLeague(dto);
    }

    // TODO : Summoner 객체로부터 최근 경기 20개 받아오기

    // TODO : 최근 경기 ID로부터 각 경기정보 받아오기

    // TODO : 생성한 Entity 저장하기
    public void saveEntities(Summoner summoner) {
        mongoTemplate.insert(summoner);
    }

}
