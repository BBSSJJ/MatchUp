package com.ssafy.matchup_statistics.summoner.service.sub;

import com.ssafy.matchup_statistics.account.entity.Account;
import com.ssafy.matchup_statistics.global.api.rest.RiotRestApiAdaptor;
import com.ssafy.matchup_statistics.global.dto.response.AccountResponseDto;
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

@Component("summonerTotalRestService")
@Slf4j
@RequiredArgsConstructor
public class SummonerTotalRestService implements SummonerTotalService {

    private final RiotRestApiAdaptor riotRestApiAdaptor;
    private final MongoTemplateAdaptor mongoTemplateAdaptor;
    private final IndicatorBuilder indicatorBuilder;
    private final LeagueMapper leagueMapper;
    private final SummonerMapper summonerMapper;

    public void save(String gameName, String tagLine) {
        long start = System.currentTimeMillis();
        AccountResponseDto accountInfo = riotRestApiAdaptor.getAccountInfo(gameName, tagLine);
        SummonerInfoResponseDto summonerInfo = riotRestApiAdaptor.getSummonerInfo(gameName, tagLine);
        LeagueInfoResponseDto leagueInfo = riotRestApiAdaptor.getLeagueInfoResponse(summonerInfo.getId());
        List<String> matchIds = riotRestApiAdaptor.getMatchIdsByPuuid(accountInfo.getPuuid());

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
                indicator.getSummonerId().toString());
        mongoTemplateAdaptor.saveSummoner(summoner);
        log.debug("소환사 1명 저장 소요시간 : {}", (System.currentTimeMillis() - start) / 1000);
    }

    @Override
    public int saveLeagueEntry(String tier) {
        return 0;
    }

    public int saveLeagueEntry(LeagueEntryRequestDto dto) {

        long totalStart = System.currentTimeMillis();

        // 리그 엔트리 돌면서 모든정보 저장
        List<LeagueInfoResponseDto> leagueInfoResponses = riotRestApiAdaptor.getLeagueInfoResponseByTier(1, dto);
        log.info("league info count(해당 리그 엔트리 소환사) : {}명", leagueInfoResponses.size());
        leagueInfoResponses.forEach(leagueInfo -> {
            long start = System.currentTimeMillis();

            SummonerInfoResponseDto summonerInfo = riotRestApiAdaptor.getSummonerInfoBySummonerName(leagueInfo.getSummonerName());
            AccountResponseDto accountInfo = riotRestApiAdaptor.getAccountInfo(summonerInfo.getPuuid());
            List<String> matchIds = riotRestApiAdaptor.getMatchIdsByPuuid(accountInfo.getPuuid());

            log.debug("매치 개수 : {}개", matchIds.size());

            // 통계 정보 생성 및 저장하기(내부에서 매치정보 저장)
            Indicator indicator = indicatorBuilder.build(matchIds, summonerInfo.getId(), summonerInfo.getPuuid());
            log.info("통계지표 생성 완료됨");
            mongoTemplateAdaptor.saveIndicator(indicator);
            log.info("created statistics - 통계지표 생성 완료 : {}", indicator.getSummonerId());

            // 소환사 정보 생성 및 저장하기
            Summoner summoner = new Summoner(
                    summonerInfo.getId(),
                    new Account(accountInfo),
                    summonerMapper.summonerInfoResponseDtoToSummonerDetail(summonerInfo),
                    leagueMapper.leagueInfoResponseDtoToLeague(leagueInfo),
                    matchIds,
                    indicator.getSummonerId().toString());
            mongoTemplateAdaptor.saveSummoner(summoner);

            log.info("created summoner(소환사 생성완료) : {}, 소요시간 : {}ms", summoner.getId(), (System.currentTimeMillis() - start));
        });

        log.info("전체 소환사 생성완료[소요시간 : {}ms]", (System.currentTimeMillis()) - totalStart);

        return 205;
    }
}
