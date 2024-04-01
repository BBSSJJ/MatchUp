package com.ssafy.matchup_statistics.summoner.service.sub.total;

import com.ssafy.matchup_statistics.account.entity.Account;
import com.ssafy.matchup_statistics.global.api.flux.RiotWebClientFactory;
import com.ssafy.matchup_statistics.global.dto.HighTierResponseDto;
import com.ssafy.matchup_statistics.global.dto.response.*;
import com.ssafy.matchup_statistics.global.exception.RiotApiError;
import com.ssafy.matchup_statistics.global.exception.RiotApiException;
import com.ssafy.matchup_statistics.indicator.entity.Indicator;
import com.ssafy.matchup_statistics.indicator.service.builder.IndicatorFluxBuilder;
import com.ssafy.matchup_statistics.league.dto.request.LeagueEntryRequestDto;
import com.ssafy.matchup_statistics.league.entity.League;
import com.ssafy.matchup_statistics.summoner.entity.Summoner;
import com.ssafy.matchup_statistics.summoner.entity.SummonerDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequiredArgsConstructor
public class SummonerTotalFluxService implements SummonerTotalService {

    private final RiotWebClientFactory riotWebClientFactory;
    private final MongoTemplate mongoTemplate;
    private final IndicatorFluxBuilder indicatorBuilder;

    public int saveLeagueEntry(LeagueEntryRequestDto dto) {

        long totalStart = System.currentTimeMillis();
        int total = 0;

        // 리그 엔트리 돌면서 모든정보 저장
        List<LeagueInfoResponseDto> leagueInfoResponseDtos = new ArrayList<>();
        for (int page = 1; page <= 6; page++) {
            List<LeagueInfoResponseDto> leagues = getLeagueEntry(page, dto).collectList().block();
            leagues.forEach(l -> leagueInfoResponseDtos.add(l));
        }
        total += leagueInfoResponseDtos.size();
        log.info("league info count(해당 리그 엔트리 소환사) : {}명", total);

        leagueInfoResponseDtos.forEach(leagueInfo -> {
            long start = System.currentTimeMillis();

                SummonerInfoResponseDto summonerInfoResponseDto = getSummonerInfo(leagueInfo).block();
                if (summonerInfoResponseDto == null) throw new RiotApiException(RiotApiError.NOT_IN_RIOT_API);

                AccountResponseDto accountResponseDto = getAccountResponseDto(summonerInfoResponseDto).block();
                if (accountResponseDto == null) throw new RiotApiException(RiotApiError.NOT_IN_RIOT_API);

                Flux<String> matches = getMatchesBySummonerInfo(summonerInfoResponseDto);

            CountDownLatch latch = new CountDownLatch(20);
            List<Tuple2<MatchDetailResponseDto, MatchTimelineResponseDto>> matchResponses = getMatchResponses(latch, matches);
            Indicator indicator = indicatorBuilder.build(matchResponses, summonerInfoResponseDto.getId(), summonerInfoResponseDto.getPuuid());

                // 통계지표 생성 완료 후 저장
                mongoTemplate.save(indicator);
                log.info("created statistics - 통계지표 생성 완료 : {}", indicator.getId());

                // 소환사 정보 생성 및 저장하기
                Summoner summoner = new Summoner(
                        summonerInfoResponseDto.getId(),
                        new Account(accountResponseDto),
                        new SummonerDetail(summonerInfoResponseDto),
                        new League(leagueInfo),
                        matches.collectList().block(),
                        true);
                mongoTemplate.save(summoner);

            log.info("created summoner(소환사 생성완료) : {}, 소요시간 : {}ms", summoner.getId(), (System.currentTimeMillis() - start));

        });

        log.info("created all summoner(전체 소환사 생성완료), 총 소요시간 : {}ms", (System.currentTimeMillis() - totalStart));
        return total;
    }

    public void save(String gameName, String tagLine) {

        long start = System.currentTimeMillis();
        AccountResponseDto accountResponseDto = getAccountResponseDto(gameName,tagLine).block();
        if (accountResponseDto == null) throw new RiotApiException(RiotApiError.NOT_IN_RIOT_API);

        SummonerInfoResponseDto summonerInfoResponseDto = getSummonerInfo(accountResponseDto).block();
        if (summonerInfoResponseDto == null) throw new RiotApiException(RiotApiError.NOT_IN_RIOT_API);

        LeagueInfoResponseDto leagueInfo = getLeagueEntry(summonerInfoResponseDto).blockFirst();
        if (leagueInfo == null) throw new RiotApiException(RiotApiError.NOT_IN_RIOT_API);

        Flux<String> matches = getMatchesBySummonerInfo(summonerInfoResponseDto);

        CountDownLatch latch = new CountDownLatch(20);
        List<Tuple2<MatchDetailResponseDto, MatchTimelineResponseDto>> matchResponses = getMatchResponses(latch, matches);
        Indicator indicator = indicatorBuilder.build(matchResponses, summonerInfoResponseDto.getId(), summonerInfoResponseDto.getPuuid());

        // 통계지표 생성 완료 후 저장
        mongoTemplate.save(indicator);
        log.info("created statistics - 통계지표 생성 완료 : {}", indicator.getId());

        // 소환사 정보 생성 및 저장하기
        Summoner summoner = new Summoner(
                summonerInfoResponseDto.getId(),
                new Account(accountResponseDto),
                new SummonerDetail(summonerInfoResponseDto),
                new League(leagueInfo),
                matches.collectList().block(),
                true);
        mongoTemplate.save(summoner);

        log.info("created summoner(소환사 생성완료) : {}, 소요시간 : {}ms", summoner.getId(), (System.currentTimeMillis() - start));

    }

    @Override
    public int saveLeagueEntry(String tier) {
        List<LeagueInfoResponseDto> highTierLeagueEntry = getHighTierLeagueEntry(tier);
        long totalStart = System.currentTimeMillis();
        int total = 0;

        highTierLeagueEntry.forEach(leagueInfo -> {
            long start = System.currentTimeMillis();

            SummonerInfoResponseDto summonerInfoResponseDto = getSummonerInfo(leagueInfo).block();
            AccountResponseDto accountResponseDto = getAccountResponseDto(summonerInfoResponseDto).block();
            Flux<String> matches = getMatchesBySummonerInfo(summonerInfoResponseDto);

            CountDownLatch latch = new CountDownLatch(20);
            List<Tuple2<MatchDetailResponseDto, MatchTimelineResponseDto>> matchResponses = getMatchResponses(latch, matches);
            Indicator indicator = indicatorBuilder.build(matchResponses, summonerInfoResponseDto.getId(), summonerInfoResponseDto.getPuuid());

            // 통계지표 생성 완료 후 저장
            mongoTemplate.save(indicator);
            log.info("created statistics - 통계지표 생성 완료 : {}", indicator.getId());

            // 소환사 정보 생성 및 저장하기
            Summoner summoner = new Summoner(
                    summonerInfoResponseDto.getId(),
                    new Account(accountResponseDto),
                    new SummonerDetail(summonerInfoResponseDto),
                    new League(leagueInfo),
                    matches.collectList().block(),
                    true);
            mongoTemplate.save(summoner);

            log.info("created summoner(소환사 생성완료) : {}, 소요시간 : {}ms", summoner.getId(), (System.currentTimeMillis() - start));

        });

        log.info("created all summoner(전체 소환사 생성완료), 총 소요시간 : {}ms", (System.currentTimeMillis() - totalStart));
        return total;
    }

    public List<LeagueInfoResponseDto> getHighTierLeagueEntry(String highTier) {
        Mono<HighTierResponseDto> highTierResponseByTier = riotWebClientFactory.getHighTierResponseByTier(highTier);
        HighTierResponseDto highTierResponseDto = highTierResponseByTier.block();
        return highTierResponseDto.getEntries();
    }

    public Flux<LeagueInfoResponseDto> getLeagueEntry(Integer pages, LeagueEntryRequestDto dto) {
        return riotWebClientFactory.getLeagueInfoResponseByTier(pages, dto);
    }

    public Flux<LeagueInfoResponseDto> getLeagueEntry(SummonerInfoResponseDto dto) {
        return riotWebClientFactory.getLeagueInfoResponseBySummonerId(dto.getId());
    }

    public Mono<AccountResponseDto> getAccountResponseDto(String gameName, String tagLine) {
        return riotWebClientFactory.getAccountInfo(gameName, tagLine);
    }

    public Mono<AccountResponseDto> getAccountResponseDto(SummonerInfoResponseDto dto) {
        return riotWebClientFactory.getAccountInfo(dto.getPuuid());
    }

    public Mono<SummonerInfoResponseDto> getSummonerInfo(LeagueInfoResponseDto dto) {
        return riotWebClientFactory.getSummonerInfoResponseDtoBySummonerId(dto.getSummonerId());
    }

    public Mono<SummonerInfoResponseDto> getSummonerInfo(AccountResponseDto accountResponseDto) {
        return riotWebClientFactory.getSummonerInfoResponseDtoByPuuid(accountResponseDto.getPuuid());
    }

    public Flux<String> getMatchesBySummonerInfo(SummonerInfoResponseDto dto) {
        return riotWebClientFactory.getMatchesResponseDtoByPuuid(dto.getPuuid());
    }

    public List<Tuple2<MatchDetailResponseDto, MatchTimelineResponseDto>> getMatchResponses(CountDownLatch latch, Flux<String> matches) {
        List<Tuple2<MatchDetailResponseDto, MatchTimelineResponseDto>> ret = new ArrayList<>();

        matches.subscribe(matchId -> {
            log.info("match Id : {}", matchId);
            Mono<MatchDetailResponseDto> matchDetailDtoMono = riotWebClientFactory.getMatchDetailResponseDtoByMatchId(matchId);
            Mono<MatchTimelineResponseDto> matchTimelineDtoMono = riotWebClientFactory.getMatchTimelineResponseDtoByMatchId(matchId);
            Mono.zip(matchDetailDtoMono, matchTimelineDtoMono).subscribe(t -> ret.add(t), throwable -> {
            }, latch::countDown);
        });

        try {
            latch.await(10_000, TimeUnit.MILLISECONDS);
            return ret;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
