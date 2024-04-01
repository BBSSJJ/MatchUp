package com.ssafy.matchup_statistics.summoner.service.sub;

import com.ssafy.matchup_statistics.global.api.flux.RiotWebClientFactory;
import com.ssafy.matchup_statistics.global.dto.response.*;
import com.ssafy.matchup_statistics.indicator.entity.Indicator;
import com.ssafy.matchup_statistics.indicator.service.builder.IndicatorBuilder;
import com.ssafy.matchup_statistics.indicator.service.builder.IndicatorFluxBuilder;
import com.ssafy.matchup_statistics.league.dto.request.LeagueEntryRequestDto;
import com.ssafy.matchup_statistics.league.type.Division;
import com.ssafy.matchup_statistics.league.type.Queue;
import com.ssafy.matchup_statistics.league.type.Tier;
import com.ssafy.matchup_statistics.summoner.service.sub.total.SummonerTotalFluxService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class SummonerTotalFluxServiceTest {

    @Autowired
    SummonerTotalFluxService target;
    @Autowired
    RiotWebClientFactory riotWebClientFactory;
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    IndicatorBuilder indicatorBuilder;
    @Autowired
    IndicatorFluxBuilder indicatorFluxBuilder;

    @Test
    @DisplayName("리그 엔트리 잘 받아오는지 테스트")
    void getLeagueEntry() {
        // given
        Integer pages = 1;
        LeagueEntryRequestDto dto = new LeagueEntryRequestDto(Division.I, Tier.DIAMOND, Queue.RANKED_SOLO_5x5);

        // when
        Flux<LeagueInfoResponseDto> responseDtoFlux = target.getLeagueEntry(pages, dto);

        // then
        responseDtoFlux.toIterable().forEach(r -> {
            log.info("response flux object : {}", r);
        });

        assertThat(responseDtoFlux.toStream().toList().size())
                .isEqualTo(205);
    }

    @Test
    @DisplayName("엔트리로부터 매치 id 리스트 받아오는지 테스트")
    void getMatches() {
        // given
        SummonerInfoResponseDto summonerInfoResponseDto = new SummonerInfoResponseDto();
        summonerInfoResponseDto.setPuuid("GweS1V-eVKk-je-x4D6znocszRr02LsMmfeOOykWurawl050dAbp3S8NcGV1JinmjysCLkS5_VOrYQ");

        // when
        Flux<String> responseDtoFlux = target.getMatchesBySummonerInfo(summonerInfoResponseDto);

        // then
        responseDtoFlux.toIterable().forEach(r -> {
            log.info("response flux object : {}", r);
        });

        assertThat(responseDtoFlux.toStream().toList().size())
                .isEqualTo(20);
    }

    @Test
    @DisplayName("매치 ID로부터 matchDetail, matchTimeline 받아오는지 테스트")
    void matchDtosTest() {
        // given
        String puuid = "GweS1V-eVKk-je-x4D6znocszRr02LsMmfeOOykWurawl050dAbp3S8NcGV1JinmjysCLkS5_VOrYQ";
        SummonerInfoResponseDto summonerInfoResponseDto = new SummonerInfoResponseDto();
        summonerInfoResponseDto.setPuuid(puuid);
        CountDownLatch latch = new CountDownLatch(20);

        // when
        Flux<String> responseDtoFlux = target.getMatchesBySummonerInfo(summonerInfoResponseDto);
        List<Tuple2<MatchDetailResponseDto, MatchTimelineResponseDto>> matchResponses = target.getMatchResponses(latch, responseDtoFlux);

        // then
        assertThat(matchResponses).size().isEqualTo(20);
        assertThat(matchResponses.get(0).getT1().getInfo().participants)
                .anySatisfy(s -> s.getPuuid().equals(puuid));
        log.info("match Detail id : {}", matchResponses.get(0).getT1().getInfo().getGameId());
        log.info("match timeline id : {}", matchResponses.get(0).getT2().getInfo().getGameId());
    }

    @Test
    @DisplayName("리그 엔트리 -> 소환사 정보 -> 매치 ID 리스트 -> 매치 Detail")
    void matchChainTest() {
        // given
        String gameName = "강찬밥";
        String tagLine = "그릇KR";
        String puuid = "GweS1V-eVKk-je-x4D6znocszRr02LsMmfeOOykWurawl050dAbp3S8NcGV1JinmjysCLkS5_VOrYQ";
        CountDownLatch latch = new CountDownLatch(20);

        // when
        long start = System.currentTimeMillis();
        AccountResponseDto accountResponseDto = target.getAccountResponseDto(gameName,tagLine).block();
        SummonerInfoResponseDto summonerInfoResponseDto = target.getSummonerInfo(accountResponseDto).block();
        Flux<String> responseDtoFlux = target.getMatchesBySummonerInfo(summonerInfoResponseDto);
        List<Tuple2<MatchDetailResponseDto, MatchTimelineResponseDto>> matchResponses = target.getMatchResponses(latch, responseDtoFlux);
        Indicator indicator = indicatorFluxBuilder.build(matchResponses, summonerInfoResponseDto.getId(), summonerInfoResponseDto.getPuuid());

        // then
        assertThat(matchResponses).size().isEqualTo(20);
        assertThat(matchResponses.get(0).getT1().getInfo().participants)
                .anySatisfy(s -> s.getPuuid().equals(puuid));
        log.info("match Detail id : {}", matchResponses.get(0).getT1().getInfo().getGameId());
        log.info("match timeline id : {}", matchResponses.get(0).getT2().getInfo().getGameId());
        assertThat(indicator.getId())
                .isEqualTo(summonerInfoResponseDto.getId());
        log.info("finished : {}", System.currentTimeMillis() - start);
    }
}