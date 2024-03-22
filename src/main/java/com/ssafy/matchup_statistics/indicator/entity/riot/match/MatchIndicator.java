package com.ssafy.matchup_statistics.indicator.entity.riot.match;

import com.ssafy.matchup_statistics.global.exception.RiotDataError;
import com.ssafy.matchup_statistics.global.exception.RiotDataException;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.beginning.*;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.end.MacroData;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.end.MacroIndicator;
import com.ssafy.matchup_statistics.match.api.dto.response.MatchDetailResponseDto;
import com.ssafy.matchup_statistics.match.api.dto.response.MatchTimelineResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class MatchIndicator {
    private Metadata metadata;
    private LaneIndicator laneIndicator;
    private MacroIndicator macroIndicator;

    public MatchIndicator(
            String puuid,
            MatchDetailResponseDto matchDetailResponseDto,
            MatchTimelineResponseDto matchTimelineResponseDto) {

        // 라인 정보 생성
        LaneInfo laneInfo = new LaneInfo(puuid, matchDetailResponseDto);

        // 라인지표 생성
        switch (laneInfo.getTeamPosition()) {
            case TOP -> this.laneIndicator = new TopIndicator(laneInfo, matchTimelineResponseDto);
            case JUNGLE -> this.laneIndicator = new JgIndicator(laneInfo, matchTimelineResponseDto);
            case MIDDLE -> this.laneIndicator = new MidIndicator(laneInfo, matchTimelineResponseDto);
            case BOTTOM -> this.laneIndicator = new BtmIndicator(laneInfo, matchTimelineResponseDto);
            case UTILITY -> this.laneIndicator = new UtilIndicator(laneInfo, matchTimelineResponseDto);
        }

        // 라인정보와 response로부터 운영 정보 생성
        MacroData macroData = new MacroData(laneInfo, matchDetailResponseDto);

        // 운영지표 생성
        this.macroIndicator = new MacroIndicator(macroData);

        // 메타데이터 생성
        this.metadata = new Metadata(laneInfo, macroData, matchTimelineResponseDto);
    }

    @Data
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
    }
}
