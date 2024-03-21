package com.ssafy.matchup_statistics.indicator.entity.riot.match.beginning;

import com.ssafy.matchup_statistics.indicator.entity.riot.match.LaneInfo;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.beginning.base.Before_15_Data;
import com.ssafy.matchup_statistics.match.api.dto.response.MatchTimelineResponseDto;

public class UtilIndicator extends LaneIndicator {
    public UtilIndicator(LaneInfo laneInfo, MatchTimelineResponseDto matchTimelineResponseDto) {
        // 15분까지 데이터 모두 확인
        Before_15_Data before15Data = new Before_15_Data(laneInfo, matchTimelineResponseDto);

        // 15분 데이터만 확인
        MatchTimelineResponseDto.ParticipantNumber my15Data = super.getMy_15Data(laneInfo, matchTimelineResponseDto);
        MatchTimelineResponseDto.ParticipantNumber opposite15Data = super.getOpposite_15Data(laneInfo, matchTimelineResponseDto);

        // 기본체급 생성
        this.getBasicWeight().setBasicWeightByDataBefore_15(before15Data);
        this.getBasicWeight().setBasicWeightByData_15(laneInfo, my15Data, opposite15Data);

        // 공격적인 라인전 생성
        this.getAggresiveLaneAbilility().setAggresiveLaneAbililityByDataBefore_15(before15Data);
        this.getAggresiveLaneAbilility().setAggresiveLaneAbililityByData_15(my15Data, opposite15Data);
    }
}
