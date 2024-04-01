package com.ssafy.matchup_statistics.summoner.service.sub.renewal;

import com.ssafy.matchup_statistics.global.api.flux.RiotWebClientFactory;
import com.ssafy.matchup_statistics.global.dto.response.MatchDetailResponseDto;
import com.ssafy.matchup_statistics.global.dto.response.MatchTimelineResponseDto;
import com.ssafy.matchup_statistics.global.exception.RiotApiError;
import com.ssafy.matchup_statistics.global.exception.RiotApiException;
import com.ssafy.matchup_statistics.indicator.entity.Indicator;
import com.ssafy.matchup_statistics.indicator.service.builder.IndicatorFluxBuilder;
import com.ssafy.matchup_statistics.summoner.dao.SummonerDao;
import com.ssafy.matchup_statistics.summoner.entity.Summoner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SummonerRenewalServiceImpl implements SummonerRenewalService {

    private final SummonerDao summonerDao;
    private final RiotWebClientFactory riotWebClientFactory;
    private final IndicatorFluxBuilder indicatorFluxBuilder;

    @Override
    @Async
    public void renew(Summoner summoner) {
        // 사용자 최근게임 정보(Indicator.matches) DB에서 조회
        Indicator indicatorInDB = summonerDao.getIndicatorInDB(summoner.getId());
        List<String> matchIdsInDB = indicatorInDB.getMatchIndicatorStatistics().getMetadata().getMatchIds();

        // 현재 라이엇에 있는 전적정보와 비교
        List<String> matchIdsInRiot = riotWebClientFactory.getMatchesResponseDtoByPuuid(summoner.getSummonerDetail().getPuuid(), 0, 100).collectList().block();
        if (matchIdsInRiot == null) throw new RiotApiException(RiotApiError.NOT_IN_RIOT_API);
        List<String> matchIdsToRenew = getMatchIdsToRenew(matchIdsInDB, matchIdsInRiot, 0, summoner.getSummonerDetail().getPuuid());

        // 없는 게임이 있다면 해당게임 정보 불러와서 매치 생성 후 전적정보에 더하기
        if (!matchIdsInRiot.isEmpty()) {
            Indicator renewedIndicator = indicatorFluxBuilder.build(getMatchInfos(matchIdsToRenew), summoner.getId(), summoner.getSummonerDetail().getPuuid());
            indicatorInDB.renew(renewedIndicator);
        }
    }

    private List<String> getMatchIdsToRenew(List<String> matchIdsInDB, List<String> matchIdsInRiot, int i, String puuid) {
        List<String> matchIdsToRenew = new ArrayList<>();

        // 하나라도 동일한 매치가 나오면 Return
        for (String matchIdInRiot : matchIdsInRiot) {
            if (matchIdsInDB.stream().anyMatch(matchIdInDB -> matchIdInDB.equals(matchIdInRiot)))
                return matchIdsToRenew;
            else matchIdsToRenew.add(matchIdInRiot);
        }

        // 동일한 매치가 하나도 없다면 재귀적으로 재실행
        if (matchIdsToRenew.size() == 100) {
            log.info("recursive!");
            i += 100;
            List<String> matchIdsInRiotRecursive = riotWebClientFactory.getMatchesResponseDtoByPuuid(puuid, i, 100).collectList().block();
            if (matchIdsInRiotRecursive == null) throw new RiotApiException(RiotApiError.NOT_IN_RIOT_API);

            matchIdsToRenew.addAll(getMatchIdsToRenew(matchIdsInDB, matchIdsInRiotRecursive, i, puuid));
        }

        return matchIdsToRenew;
    }

    private List<Tuple2<MatchDetailResponseDto, MatchTimelineResponseDto>> getMatchInfos(List<String> matches) {
        List<Tuple2<MatchDetailResponseDto, MatchTimelineResponseDto>> ret = new ArrayList<>();

        matches.forEach(matchId -> {
            log.info("match Id : {}", matchId);
            Mono<MatchDetailResponseDto> matchDetailDtoMono = riotWebClientFactory.getMatchDetailResponseDtoByMatchId(matchId);
            Mono<MatchTimelineResponseDto> matchTimelineDtoMono = riotWebClientFactory.getMatchTimelineResponseDtoByMatchId(matchId);
            Mono.zip(matchDetailDtoMono, matchTimelineDtoMono).subscribe(ret::add);
        });

        return ret;

    }
}
