package com.ssafy.matchup_statistics.indicator.entity.match.beginning;

import com.ssafy.matchup_statistics.global.dto.response.MatchTimelineResponseDto;
import com.ssafy.matchup_statistics.indicator.entity.match.LaneInfo;
import com.ssafy.matchup_statistics.indicator.entity.match.beginning.base.AggresiveJgAbility;
import com.ssafy.matchup_statistics.indicator.data.Before_15_Data;
import com.ssafy.matchup_statistics.indicator.entity.match.beginning.base.LaneAssist;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@Getter
public class JgIndicator extends LaneIndicator {
    private LaneAssist laneAssist;
    private AggresiveJgAbility aggresiveJgAbility;

    public JgIndicator(LaneInfo laneInfo, MatchTimelineResponseDto matchTimelineResponseDto) {
        super(laneInfo, matchTimelineResponseDto);
    }

    public void addJgIndicator(Before_15_Data before15Data,
                               MatchTimelineResponseDto.ParticipantNumber my15Data,
                               MatchTimelineResponseDto.ParticipantNumber opposite15Data) {
        laneAssist = new LaneAssist();
        aggresiveJgAbility = new AggresiveJgAbility();

        // 라인 어시스트 생성
        laneAssist.setLaneAssistByDataBefore_15(before15Data);

        // 공격적인 라인전 생성
        aggresiveJgAbility.setAggresiveLaneAbililityByDataBefore_15(before15Data);
        aggresiveJgAbility.setAggresiveLaneAbililityByData_15(my15Data, opposite15Data);
    }
}
