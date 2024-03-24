package com.ssafy.matchup_statistics.indicator.entity.match;

import com.ssafy.matchup_statistics.global.dto.response.MatchDetailResponseDto;
import com.ssafy.matchup_statistics.global.dto.response.MatchTimelineResponseDto;
import com.ssafy.matchup_statistics.indicator.entity.match.beginning.*;
import com.ssafy.matchup_statistics.indicator.data.MacroData;
import com.ssafy.matchup_statistics.indicator.entity.match.end.MacroIndicator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class MatchIndicator {
    private String matchId;
    private Metadata metadata;
    private LaneIndicator laneIndicator;
    private MacroIndicator macroIndicator;

    public MatchIndicator(
            String puuid,
            MatchDetailResponseDto matchDetailResponseDto,
            MatchTimelineResponseDto matchTimelineResponseDto) {
        // 매치아이디 받아오기
        matchId = matchDetailResponseDto.getMetadata().getMatchId();

        // 라인 정보 생성
        LaneInfo laneInfo = new LaneInfo(puuid, matchDetailResponseDto);

        log.debug("lane info(라인정보) 생성 완료 : {}", laneInfo);

        // 라인지표 생성
        switch (laneInfo.getTeamPosition()) {
            case TOP -> this.laneIndicator = new TopIndicator(laneInfo, matchTimelineResponseDto);
            case JUNGLE -> this.laneIndicator = new JgIndicator(laneInfo, matchTimelineResponseDto);
            case MIDDLE -> this.laneIndicator = new MidIndicator(laneInfo, matchTimelineResponseDto);
            case BOTTOM -> this.laneIndicator = new BtmIndicator(laneInfo, matchTimelineResponseDto);
            case UTILITY -> this.laneIndicator = new UtilIndicator(laneInfo, matchTimelineResponseDto);
        }

        log.debug("lane indicator(라인지표) 생성 완료 : {}", laneIndicator);

        // 라인정보와 response로부터 운영 정보 생성
        MacroData macroData = new MacroData(laneInfo, matchDetailResponseDto);

        log.debug("macro data(운영 정보) 생성 완료 : {}", macroData);

        // 운영지표 생성
        this.macroIndicator = new MacroIndicator(macroData);

        log.debug("macro indicator(운영지표) 생성 완료 : {}", macroIndicator);

        // 메타데이터 생성
        this.metadata = new Metadata(laneInfo, macroData, matchTimelineResponseDto);

        log.debug("metadata(메타데이터) 생성 완료 : {}", metadata);
    }

    public MatchIndicator(String matchId, boolean isFinishedBeforeFifteen) {
        this.matchId = matchId;
        this.metadata = new Metadata(isFinishedBeforeFifteen);
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Metadata {
        private LaneInfo laneInfo;
        private boolean isFinishedBeforeFifteen;
        private boolean isOurTeamEarlySurrendered;
        private int pingCount;

        public Metadata(LaneInfo laneInfo,
                        MacroData macroData,
                        MatchTimelineResponseDto matchTimelineResponseDto) {
            this.laneInfo = laneInfo;
            isFinishedBeforeFifteen = matchTimelineResponseDto.getInfo().getFrames().size() <= 15;
            isOurTeamEarlySurrendered = macroData.getMyData().teamEarlySurrendered;
            pingCount = macroData.getMyData().getAllInPings();
        }

        public Metadata(boolean isFinishedBeforeFifteen) {
            this.isFinishedBeforeFifteen = isFinishedBeforeFifteen;
        }
    }
}
