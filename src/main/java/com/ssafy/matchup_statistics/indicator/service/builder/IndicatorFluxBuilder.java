package com.ssafy.matchup_statistics.indicator.service.builder;

import com.ssafy.matchup_statistics.global.dto.response.MatchDetailResponseDto;
import com.ssafy.matchup_statistics.global.dto.response.MatchTimelineResponseDto;
import com.ssafy.matchup_statistics.global.exception.RiotDataError;
import com.ssafy.matchup_statistics.global.exception.RiotDataException;
import com.ssafy.matchup_statistics.indicator.entity.Indicator;
import com.ssafy.matchup_statistics.indicator.entity.match.MatchIndicator;
import com.ssafy.matchup_statistics.indicator.entity.match.MatchIndicatorStatistics;
import com.ssafy.matchup_statistics.indicator.entity.match.TeamPosition;
import com.ssafy.matchup_statistics.indicator.entity.match.TimeInfo;
import com.ssafy.matchup_statistics.match.dao.MatchDaoImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import reactor.util.function.Tuple2;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class IndicatorFluxBuilder {

    private final MongoTemplate mongoTemplate;

    public Indicator build(List<Tuple2<MatchDetailResponseDto, MatchTimelineResponseDto>> matchResponses, String summonerId, String puuid) {
        List<MatchIndicator> matchIndicators = new ArrayList<>();
        log.info("매치 지표 생성시작");

        matchResponses.forEach(matchResponse -> {
            long start = System.currentTimeMillis();

            MatchDetailResponseDto matchDetailResponseDtoByMatchId = matchResponse.getT1();
            MatchTimelineResponseDto matchTimelineResponseDtoByMatchId = matchResponse.getT2();

            String matchId = "KR_" + matchDetailResponseDtoByMatchId.getInfo().getGameId();
            log.debug("매치 id({}) 분석 시작", matchId);

            // 매치정보는 별도로 저장
            mongoTemplate.save(matchDetailResponseDtoByMatchId);

            // 15분 이전에 끝난 게임 처리
            if (matchTimelineResponseDtoByMatchId.getInfo().getFrames().size() <= 15) {
                TimeInfo timeInfo = new TimeInfo(matchDetailResponseDtoByMatchId.getInfo().gameDuration,
                        matchDetailResponseDtoByMatchId.getInfo().getGameStartTimestamp(),
                        matchDetailResponseDtoByMatchId.getInfo().getGameEndTimestamp());
                matchIndicators.add(new MatchIndicator(matchId, timeInfo, true));
                log.debug("15분 이전에 종료한 게임 : {}", matchId);
                return;
            }

            // teamPosition이 없는 경우 처리
            try {
                matchDetailResponseDtoByMatchId.getInfo().getParticipants().forEach(p -> {
                    if (!EnumUtils.isValidEnumIgnoreCase(TeamPosition.class, p.getTeamPosition()))
                        throw new RiotDataException(RiotDataError.ILLEGAL_TEAM_POSITION_ERROR);
                });
            } catch (RiotDataException riotDataException) {
                TimeInfo timeInfo = new TimeInfo(matchDetailResponseDtoByMatchId.getInfo().gameDuration,
                        matchDetailResponseDtoByMatchId.getInfo().getGameStartTimestamp(),
                        matchDetailResponseDtoByMatchId.getInfo().getGameEndTimestamp());
                matchIndicators.add(new MatchIndicator(matchId, timeInfo, false));
                log.debug("포지션 정보를 받아올 수 없는 게임 : {}", matchId);
                return;
            }

            // 세부정보를 통해 매치 지표 생성
            MatchIndicator matchIndicator = new MatchIndicator(puuid, matchDetailResponseDtoByMatchId, matchTimelineResponseDtoByMatchId);
            log.debug("matchIndicator(매치 지표) 생성완료 : {}ms 소요", System.currentTimeMillis() - start);

            matchIndicators.add(matchIndicator);
        });
        log.debug("생성된 매치지표 : {}", matchIndicators);
        return new Indicator(summonerId, matchIndicators);
    }
}
