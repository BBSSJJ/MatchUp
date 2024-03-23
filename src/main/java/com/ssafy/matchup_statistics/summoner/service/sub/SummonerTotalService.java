package com.ssafy.matchup_statistics.summoner.service.sub;

import com.ssafy.matchup_statistics.account.dto.response.AccountResponseDto;
import com.ssafy.matchup_statistics.account.entity.Account;
import com.ssafy.matchup_statistics.global.api.RiotApiAdaptor;
import com.ssafy.matchup_statistics.global.dto.response.LeagueInfoResponseDto;
import com.ssafy.matchup_statistics.global.dto.response.SummonerInfoResponseDto;
import com.ssafy.matchup_statistics.global.util.MongoTemplateAdaptor;
import com.ssafy.matchup_statistics.global.util.mapper.LeagueMapper;
import com.ssafy.matchup_statistics.global.util.mapper.SummonerMapper;
import com.ssafy.matchup_statistics.indicator.entity.Indicator;
import com.ssafy.matchup_statistics.indicator.service.builder.IndicatorBuilder;
import com.ssafy.matchup_statistics.league.dto.request.LeagueEntryRequestDto;
import com.ssafy.matchup_statistics.summoner.entity.Summoner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class SummonerTotalService {

    private final RiotApiAdaptor riotApiAdaptor;
    private final MongoTemplateAdaptor mongoTemplateAdaptor;
    private final IndicatorBuilder indicatorBuilder;
    private final LeagueMapper leagueMapper;
    private final SummonerMapper summonerMapper;

    public void save(String gameName, String tagLine) {
        long start = System.currentTimeMillis();
        AccountResponseDto accountInfo = riotApiAdaptor.getAccountInfo(gameName, tagLine);
        SummonerInfoResponseDto summonerInfo = riotApiAdaptor.getSummonerInfo(gameName, tagLine);
        LeagueInfoResponseDto leagueInfo = riotApiAdaptor.getLeagueInfoResponse(summonerInfo.getId());
        List<String> matchIds = riotApiAdaptor.getMatchIdsByPuuid(accountInfo.getPuuid());

        // 통계 정보 생성 및 저장하기(내부에서 매치정보 저장)
        Indicator indicator = indicatorBuilder.build(matchIds, summonerInfo.getId(), summonerInfo.getPuuid());
        mongoTemplateAdaptor.saveIndicator(indicator);

        // 소환사 정보 생성 및 저장하기
        Summoner summoner = new Summoner(
                summonerInfo.getId(),
                new Account(accountInfo),
                summonerMapper.summonerInfoResponseDtoToSummonerDetail(summonerInfo),
                leagueMapper.leagueInfoResponseDtoToLeague(leagueInfo),
                matchIds,
                indicator.getId().toString());
        mongoTemplateAdaptor.saveSummoner(summoner);
        log.debug("소환사 1명 저장 소요시간 : {}", (System.currentTimeMillis() - start) / 1000);
    }

    public int saveLeagueEntry(Integer pages, LeagueEntryRequestDto dto) {

        // 리그 엔트리 돌면서 모든정보 저장
        for (int i = 1; i <pages + 1; i++) {
            List<LeagueInfoResponseDto> leagueInfoResponses = riotApiAdaptor.getLeagueInfoResponseByTier(dto, i);
            log.info("league info count(해당 리그 엔트리 소환사) : {}명", leagueInfoResponses.size());
            leagueInfoResponses.forEach(leagueInfo -> {
                long start = System.currentTimeMillis();

                SummonerInfoResponseDto summonerInfo = riotApiAdaptor.getSummonerInfoBySummonerName(leagueInfo.getSummonerName());
                AccountResponseDto accountInfo = riotApiAdaptor.getAccountInfo(summonerInfo.getPuuid());
                List<String> matchIds = riotApiAdaptor.getMatchIdsByPuuid(accountInfo.getPuuid());
                log.debug("매치 개수 : {}개", matchIds.size());

                // 통계 정보 생성 및 저장하기(내부에서 매치정보 저장)
                Indicator indicator = indicatorBuilder.build(matchIds, summonerInfo.getId(), summonerInfo.getPuuid());
                log.info("통계지표 생성 완료됨");
                mongoTemplateAdaptor.saveIndicator(indicator);
                log.info("created statistics - 통계지표 생성 완료 : {}", indicator.getId());

                // 소환사 정보 생성 및 저장하기
                Summoner summoner = new Summoner(
                        summonerInfo.getId(),
                        new Account(accountInfo),
                        summonerMapper.summonerInfoResponseDtoToSummonerDetail(summonerInfo),
                        leagueMapper.leagueInfoResponseDtoToLeague(leagueInfo),
                        matchIds,
                        indicator.getId().toString());
                mongoTemplateAdaptor.saveSummoner(summoner);

                log.info("created summoner(소환사 생성완료) : {}, 소요시간 : {}ms", summoner.getId(), (System.currentTimeMillis() - start));
            });
        }
        return pages;
    }
}
