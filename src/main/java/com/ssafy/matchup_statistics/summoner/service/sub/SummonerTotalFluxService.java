package com.ssafy.matchup_statistics.summoner.service.sub;

import com.ssafy.matchup_statistics.account.entity.Account;
import com.ssafy.matchup_statistics.global.api.flux.RiotWebClientFactory;
import com.ssafy.matchup_statistics.global.dto.response.*;
import com.ssafy.matchup_statistics.global.util.MongoTemplateAdaptor;
import com.ssafy.matchup_statistics.global.util.mapper.LeagueMapper;
import com.ssafy.matchup_statistics.global.util.mapper.SummonerMapper;
import com.ssafy.matchup_statistics.indicator.entity.Indicator;
import com.ssafy.matchup_statistics.indicator.service.builder.IndicatorFluxBuilder;
import com.ssafy.matchup_statistics.league.dto.request.LeagueEntryRequestDto;
import com.ssafy.matchup_statistics.summoner.entity.Summoner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final MongoTemplateAdaptor mongoTemplateAdaptor;
    private final IndicatorFluxBuilder indicatorBuilder;
    private final LeagueMapper leagueMapper;
    private final SummonerMapper summonerMapper;

    public int saveLeagueEntry(Integer pages, LeagueEntryRequestDto dto) {

        long totalStart = System.currentTimeMillis();
        int total = 0;

        // 리그 엔트리 돌면서 모든정보 저장
        for (int i = 1; i <pages + 1; i++) {

            List<LeagueInfoResponseDto> leagueInfoResponseDtos = getLeagueEntry(pages, dto).collectList().block();
            total += leagueInfoResponseDtos.size();
            log.info("league info count(해당 리그 엔트리 소환사) : {}명", total);

            leagueInfoResponseDtos.forEach(leagueInfo -> {
                long start = System.currentTimeMillis();

                SummonerInfoResponseDto summonerInfoResponseDto = getSummonerInfo(leagueInfo).block();
                AccountResponseDto accountResponseDto = getAccountResponseDto(summonerInfoResponseDto).block();
                Flux<String> matches = getMatchesBySummonerInfo(summonerInfoResponseDto);

                CountDownLatch latch = new CountDownLatch(20);
                List<Tuple2<MatchDetailResponseDto, MatchTimelineResponseDto>> matchResponses = getMatchResponses(latch, matches);
                Indicator indicator = indicatorBuilder.build(matchResponses, summonerInfoResponseDto.getId(), summonerInfoResponseDto.getPuuid());

                // 통계지표 생성 완료 후 저장
                mongoTemplateAdaptor.saveIndicator(indicator);
                log.info("created statistics - 통계지표 생성 완료 : {}", indicator.getSummonerId());

                // 소환사 정보 생성 및 저장하기
                Summoner summoner = new Summoner(
                        summonerInfoResponseDto.getId(),
                        new Account(accountResponseDto),
                        summonerMapper.summonerInfoResponseDtoToSummonerDetail(summonerInfoResponseDto),
                        leagueMapper.leagueInfoResponseDtoToLeague(leagueInfo),
                        matches.collectList().block(),
                        indicator.getSummonerId().toString());
                mongoTemplateAdaptor.saveSummoner(summoner);

                log.info("created summoner(소환사 생성완료) : {}, 소요시간 : {}ms", summoner.getId(), (System.currentTimeMillis() - start));

            });
        }
        log.info("created all summoner(전체 소환사 생성완료), 총 소요시간 : {}ms", (System.currentTimeMillis() - totalStart));
        return total;
    }

    public void save(String gameName, String tagLine) {

        long start = System.currentTimeMillis();
        AccountResponseDto accountResponseDto = getAccountResponseDto(gameName,tagLine).block();
        SummonerInfoResponseDto summonerInfoResponseDto = getSummonerInfo(accountResponseDto).block();
        LeagueInfoResponseDto leagueInfoResponseDto = getLeagueEntry(summonerInfoResponseDto).blockFirst();
        Flux<String> matches = getMatchesBySummonerInfo(summonerInfoResponseDto);

        CountDownLatch latch = new CountDownLatch(20);
        List<Tuple2<MatchDetailResponseDto, MatchTimelineResponseDto>> matchResponses = getMatchResponses(latch, matches);
        Indicator indicator = indicatorBuilder.build(matchResponses, summonerInfoResponseDto.getId(), summonerInfoResponseDto.getPuuid());

        // 통계지표 생성 완료 후 저장
        mongoTemplateAdaptor.saveIndicator(indicator);
        log.info("created statistics - 통계지표 생성 완료 : {}", indicator.getSummonerId());

        // 소환사 정보 생성 및 저장하기
        Summoner summoner = new Summoner(
                summonerInfoResponseDto.getId(),
                new Account(accountResponseDto),
                summonerMapper.summonerInfoResponseDtoToSummonerDetail(summonerInfoResponseDto),
                leagueMapper.leagueInfoResponseDtoToLeague(leagueInfoResponseDto),
                matches.collectList().block(),
                indicator.getSummonerId().toString());
        mongoTemplateAdaptor.saveSummoner(summoner);

        log.info("created summoner(소환사 생성완료) : {}, 소요시간 : {}ms", summoner.getId(), (System.currentTimeMillis() - start));

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
        return riotWebClientFactory.getSummonerInfoResponseDtoBySummonerName(dto.getSummonerName());
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
            latch.await(500_000, TimeUnit.MILLISECONDS);
            return ret;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
