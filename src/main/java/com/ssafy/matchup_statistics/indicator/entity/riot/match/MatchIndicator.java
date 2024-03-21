package com.ssafy.matchup_statistics.indicator.entity.riot.match;

import com.ssafy.matchup_statistics.indicator.entity.riot.match.beginning.*;
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

        // 메타데이터 생성
        LaneInfo laneInfo = new LaneInfo(puuid, matchDetailResponseDto);
        this.metadata = new Metadata(laneInfo, matchTimelineResponseDto);

        // 세부 지표 생성

        // 라인지표
        switch (laneInfo.getTeamPosition()) {
            case TOP:
                this.laneIndicator = new TopIndicator(laneInfo, matchTimelineResponseDto);
                break;
            case JUNGLE:
                this.laneIndicator = new JgIndicator(laneInfo, matchTimelineResponseDto);
                break;
            case MIDDLE:
                this.laneIndicator = new MidIndicator(laneInfo, matchTimelineResponseDto);
                break;
            case BOTTOM:
                this.laneIndicator = new BtmIndicator(laneInfo, matchTimelineResponseDto);
                break;
            case UTILITY:
                this.laneIndicator = new UtilIndicator(laneInfo, matchTimelineResponseDto);
                break;
        }

        // 운영지표
        this.macroIndicator = new MacroIndicator(matchDetailResponseDto);
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class Metadata {
        private LaneInfo laneInfo;
        private Boolean isFinishedBeforeFifteen;
        private Long pingCount;
        private Long earlySurrenderCount;
        private Long itemGetTime;

        public Metadata(LaneInfo laneInfo, MatchTimelineResponseDto matchTimelineResponseDto) {
            this.laneInfo = laneInfo;
            this.isFinishedBeforeFifteen = matchTimelineResponseDto.getInfo().getFrames().size() <= 15;
        }
    }
}
