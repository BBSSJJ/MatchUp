package com.ssafy.matchup_statistics.indicator.service.builder.riot.match;

import com.ssafy.matchup_statistics.indicator.entity.riot.match.MatchIndicator;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.beginning.DetailLaneIndicator;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.end.DetailEtcIndicator;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.end.DetailMacroIndicator;
import com.ssafy.matchup_statistics.indicator.service.builder.riot.match.beginning.LaneIndicatorBuilder;
import com.ssafy.matchup_statistics.indicator.service.builder.riot.match.end.EtcIndicatorBuilder;
import com.ssafy.matchup_statistics.indicator.service.builder.riot.match.end.MacroIndicatorBuilder;
import com.ssafy.matchup_statistics.match.api.MatchRestApi;
import com.ssafy.matchup_statistics.match.api.dto.response.MatchDetailResponseDto;
import com.ssafy.matchup_statistics.match.api.dto.response.MatchTimelineResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MatchIndicatorBuilder {
    private final MatchRestApi matchRestApi;
    private final LaneIndicatorBuilder laneIndicatorBuilder;
    private final MacroIndicatorBuilder macroIndicatorBuilder;
    private final EtcIndicatorBuilder etcIndicatorBuilder;

    public MatchIndicator build(String myPuuid, List<String> matches) {
        MatchIndicator matchIndicator = new MatchIndicator();

        // 매치로부터 운영, 기타지표 생성
        matches.stream().forEach(matchId -> {
            // 매치 받아오기
            MatchDetailResponseDto matchDto = matchRestApi.getMatchDetailResponseDtoByMatchId(matchId);

            // 바텀라인인지 확인
            checkMyLaneIsBottomLane(matchDto, matchIndicator);

            // 본인, 상대방, 듀오 라인번호 저장
            checkMyLaneNumber(myPuuid, matchDto, matchIndicator);
            checkOppositeLaneNumber(myPuuid, matchDto, matchIndicator);
            checkBottomDuoLaneNumbers(myPuuid, matchDto, matchIndicator);

            // 운영지표 생성
            DetailMacroIndicator detailMacroIndicator = macroIndicatorBuilder.build(matchDto);
            macroIndicatorBuilder.add(matchIndicator.getMacroIndicator(), detailMacroIndicator);

            // 기타지표 생성
            DetailEtcIndicator detailEtcIndicator = etcIndicatorBuilder.build(matchDto);
            etcIndicatorBuilder.add(matchIndicator.getEtcIndicator(), detailEtcIndicator);
        });

        // 매치로부터 라인전 지표 생성
        matches.stream().forEach(matchId -> {
            // 매치 타임라인 받아오기
            MatchTimelineResponseDto matchTimelineDto = matchRestApi.getMatchTimelineResponseDtoByMatchId(matchId);

            // 라인지표 생성
            DetailLaneIndicator detailLaneIndicator = laneIndicatorBuilder.build(
                    matchIndicator.getIsBottomLane(),
                    matchIndicator.getMyLaneNumber(),
                    matchIndicator.getOppositeLaneNumber(),
                    matchIndicator.getBottomDuoLaneNumbers(),
                    matchTimelineDto);
            laneIndicatorBuilder.add(matchIndicator.getLaneIndicator(), detailLaneIndicator);
        });

        return matchIndicator;
    }

    private void checkMyLaneNumber(String myPuuid, MatchDetailResponseDto matchDto, MatchIndicator matchIndicator) {
        // TODO : 본인 라인 확인 후 저장하는 테스트코드 작성
    }

    private void checkOppositeLaneNumber(String myPuuid, MatchDetailResponseDto matchDto, MatchIndicator matchIndicator) {
        // TODO : 상대방 라인 확인 후 저장하는 테스트코드 작성
    }

    private void checkBottomDuoLaneNumbers(String myPuuid, MatchDetailResponseDto matchDto, MatchIndicator matchIndicator) {
        // TODO : 듀오 라인 확인 후 저장하는 테스트코드 작성
    }

    protected void checkMyLaneIsBottomLane(MatchDetailResponseDto match, MatchIndicator matchIndicator) {
        // TODO : 바텀라인인지 확인 후 저장하는 테스트코드 작성
    }

}
