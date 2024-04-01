package com.ssafy.matchup_statistics.summoner.service.builder;

import com.ssafy.matchup_statistics.account.entity.Account;
import com.ssafy.matchup_statistics.global.api.flux.RiotWebClientFactory;
import com.ssafy.matchup_statistics.global.dto.response.*;
import com.ssafy.matchup_statistics.global.exception.RiotApiError;
import com.ssafy.matchup_statistics.global.exception.RiotApiException;
import com.ssafy.matchup_statistics.indicator.entity.Indicator;
import com.ssafy.matchup_statistics.indicator.service.builder.IndicatorFluxBuilder;
import com.ssafy.matchup_statistics.league.entity.League;
import com.ssafy.matchup_statistics.summoner.dto.response.SummonerRecordInfoResponseDto;
import com.ssafy.matchup_statistics.summoner.entity.Summoner;
import com.ssafy.matchup_statistics.summoner.entity.SummonerDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class SummonerBuilderImpl implements SummonerBuilder {

    private final RiotWebClientFactory riotWebClientFactory;
    private final IndicatorFluxBuilder indicatorFluxBuilder;
    private final MongoTemplate mongoTemplate;

    @Override
    public Summoner build(String gameName, String tagLine) {

        // 소환사 정보 받아오기
        Triple<AccountResponseDto, SummonerInfoResponseDto, LeagueInfoResponseDto> summonerDatas = getSummonerDatas(gameName, tagLine);

        // 매치 받아오기
        Pair<List<Tuple2<MatchDetailResponseDto, MatchTimelineResponseDto>>, List<String>> matches = getMatches(summonerDatas.getMiddle().getPuuid());

        // 통계정보 생성 및 저장하기
        Indicator indicator = indicatorFluxBuilder.build(matches.getLeft(), summonerDatas.getMiddle().getId(), summonerDatas.getMiddle().getPuuid());
        mongoTemplate.save(indicator);

        // 소환사정보 생성 및 저장하기
        Summoner summoner = build(summonerDatas, matches);
        mongoTemplate.save(summoner);
        log.info("소환사 생성완료(소환사명 : {}, pk : {})", summonerDatas.getLeft().getGameName() + "#" + summonerDatas.getLeft().getTagLine(), summoner.getId());

        // 소환사정보 반환
        return summoner;
    }

    @Override
    public Summoner build(String summonerId) {

        // 소환사 정보 받아오기
        Triple<AccountResponseDto, SummonerInfoResponseDto, LeagueInfoResponseDto> summonerDatas = getSummonerDatas(summonerId);

        // 매치 받아오기
        Pair<List<Tuple2<MatchDetailResponseDto, MatchTimelineResponseDto>>, List<String>> matches = getMatches(summonerDatas.getMiddle().getPuuid());

        // 통계정보 생성 및 저장하기
        Indicator indicator = indicatorFluxBuilder.build(matches.getLeft(), summonerDatas.getMiddle().getId(), summonerDatas.getMiddle().getPuuid());
        mongoTemplate.save(indicator);

        // 소환사정보 생성 및 저장하기
        Summoner summoner = build(summonerDatas, matches);
        mongoTemplate.save(summoner);
        log.info("소환사 생성완료(소환사명 : {}, pk : {})", summonerDatas.getLeft().getGameName() + "#" + summonerDatas.getLeft().getTagLine(), summoner.getId());

        // 소환사정보 반환
        return summoner;
    }

    @Override
    public SummonerRecordInfoResponseDto buildRecord(String gameName, String tagLine) {

        // 소환사 정보 받아오기
        Triple<AccountResponseDto, SummonerInfoResponseDto, LeagueInfoResponseDto> summonerDatas = getSummonerDatas(gameName, tagLine);

        // 매치 받아오기
        Pair<List<Tuple2<MatchDetailResponseDto, MatchTimelineResponseDto>>, List<String>> matches = getMatches(summonerDatas.getMiddle().getPuuid());

        // 통계정보 생성 및 저장하기
        Indicator indicator = indicatorFluxBuilder.build(matches.getLeft(), summonerDatas.getMiddle().getId(), summonerDatas.getMiddle().getPuuid());
        mongoTemplate.save(indicator);
        log.info("지표 생성완료(소환사명 : {}, pk : {})", summonerDatas.getLeft().getGameName() + "#" + summonerDatas.getLeft().getTagLine(), indicator.getId());

        // 소환사정보 생성 및 저장하기
        Summoner summoner = build(summonerDatas, matches);
        mongoTemplate.save(summoner);
        log.info("소환사 생성완료(소환사명 : {}, pk : {})", summonerDatas.getLeft().getGameName() + "#" + summonerDatas.getLeft().getTagLine(), summoner.getId());

        // 전적정보 반환
        return new SummonerRecordInfoResponseDto(
                summonerDatas.getMiddle(), summonerDatas.getRight(),
                matches.getLeft().stream().map(Tuple2::getT1).toList(),
                indicator);
    }

    private Summoner build(Triple<AccountResponseDto, SummonerInfoResponseDto, LeagueInfoResponseDto> summonerDatas, Pair<List<Tuple2<MatchDetailResponseDto, MatchTimelineResponseDto>>, List<String>> matches) {
        return new Summoner(
                summonerDatas.getMiddle().getId(),
                new Account(summonerDatas.getLeft()),
                new SummonerDetail(summonerDatas.getMiddle()),
                new League(summonerDatas.getRight()),
                matches.getRight(),
                true);
    }

    private Triple<AccountResponseDto, SummonerInfoResponseDto, LeagueInfoResponseDto> getSummonerDatas(String summonerId) {

        LeagueInfoResponseDto leagueInfo = riotWebClientFactory.getLeagueInfoResponseBySummonerId(summonerId).blockFirst();
        if (leagueInfo == null) throw new RiotApiException(RiotApiError.NOT_IN_RIOT_API);

        SummonerInfoResponseDto summonerInfo = riotWebClientFactory.getSummonerInfoResponseDtoBySummonerName(leagueInfo.getSummonerName()).block();
        if (summonerInfo == null) throw new RiotApiException(RiotApiError.NOT_IN_RIOT_API);

        AccountResponseDto accountInfo = riotWebClientFactory.getAccountInfo(summonerInfo.getPuuid()).block();
        if (accountInfo == null) throw new RiotApiException(RiotApiError.NOT_IN_RIOT_API);

        return Triple.of(accountInfo, summonerInfo, leagueInfo);
    }

    private Triple<AccountResponseDto, SummonerInfoResponseDto, LeagueInfoResponseDto> getSummonerDatas(String gameName, String tagLine) {
        AccountResponseDto accountInfo = riotWebClientFactory.getAccountInfo(gameName, tagLine).block();
        if (accountInfo == null) throw new RiotApiException(RiotApiError.NOT_IN_RIOT_API);

        SummonerInfoResponseDto summonerInfo = riotWebClientFactory.getSummonerInfoResponseDtoByPuuid(accountInfo.getPuuid()).block();
        if (summonerInfo == null) throw new RiotApiException(RiotApiError.NOT_IN_RIOT_API);

        LeagueInfoResponseDto leagueInfo = riotWebClientFactory.getLeagueInfoResponseBySummonerId(summonerInfo.getId()).blockFirst();
        if (leagueInfo == null) throw new RiotApiException(RiotApiError.NOT_IN_RIOT_API);

        return Triple.of(accountInfo, summonerInfo, leagueInfo);
    }

    private Pair<List<Tuple2<MatchDetailResponseDto, MatchTimelineResponseDto>>, List<String>> getMatches(String puuid) {
        List<Tuple2<MatchDetailResponseDto, MatchTimelineResponseDto>> matchDtoTuple = new ArrayList<>();
        List<String> matchIdList = new ArrayList<>();
        Flux<String> matchIds = riotWebClientFactory.getMatchesResponseDtoByPuuid(puuid);
        CountDownLatch latch = new CountDownLatch(20);

        matchIds.subscribe(matchId -> {
            matchIdList.add(matchId);
            Mono<MatchDetailResponseDto> matchDetailDtoMono = riotWebClientFactory.getMatchDetailResponseDtoByMatchId(matchId);
            Mono<MatchTimelineResponseDto> matchTimelineDtoMono = riotWebClientFactory.getMatchTimelineResponseDtoByMatchId(matchId);
            Mono.zip(matchDetailDtoMono, matchTimelineDtoMono).subscribe(matchDtoTuple::add, throwable -> {
            }, latch::countDown);
        });

        try {
            latch.await(10_000, TimeUnit.MILLISECONDS);
            return Pair.of(matchDtoTuple, matchIdList);
        } catch (InterruptedException e) {
            // 최근 매치가 1개도 없을때
            if (matchDtoTuple.isEmpty()) throw new RiotApiException(RiotApiError.NO_RECENT_GAME_ERROR);
            throw new RuntimeException(e);
        }
    }
}
