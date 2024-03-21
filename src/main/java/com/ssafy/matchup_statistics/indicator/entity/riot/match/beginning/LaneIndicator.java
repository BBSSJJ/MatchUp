package com.ssafy.matchup_statistics.indicator.entity.riot.match.beginning;

import com.ssafy.matchup_statistics.indicator.entity.riot.match.LaneInfo;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.LaneType;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.TeamPosition;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.beginning.base.AggresiveLaneAbilility;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.beginning.base.BasicWeight;
import com.ssafy.matchup_statistics.match.api.dto.response.MatchTimelineResponseDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@Slf4j
public abstract class LaneIndicator {
    private BasicWeight basicWeight = new BasicWeight();
    private AggresiveLaneAbilility aggresiveLaneAbilility = new AggresiveLaneAbilility();

    protected MatchTimelineResponseDto.ParticipantNumber getMy_15Data(LaneInfo laneInfo, MatchTimelineResponseDto matchTimelineResponseDto) {
        return matchTimelineResponseDto.getInfo().getFrames().get(15).getParticipantFrames()
                .getDataByNumber(laneInfo.getMyLaneNumber());
    }

    protected MatchTimelineResponseDto.ParticipantNumber getOpposite_15Data(LaneInfo laneInfo, MatchTimelineResponseDto matchTimelineResponseDto) {
        return matchTimelineResponseDto.getInfo().getFrames().get(15).getParticipantFrames()
                .getDataByNumber(laneInfo.getOppositeLaneNumber());
    }
}
