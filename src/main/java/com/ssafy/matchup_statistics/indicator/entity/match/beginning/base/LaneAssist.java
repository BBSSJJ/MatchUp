package com.ssafy.matchup_statistics.indicator.entity.match.beginning.base;

import com.ssafy.matchup_statistics.indicator.data.Before_15_Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class LaneAssist {
    private int killAssistDiffer;
    private int killInvolvementRate;

    public LaneAssist(List<LaneAssist> laneAssists) {
        if (laneAssists.isEmpty()) return;
        laneAssists.forEach(laneAssist -> {
            killAssistDiffer += laneAssist.getKillAssistDiffer();
            killInvolvementRate += laneAssist.getKillInvolvementRate();
        });
        killAssistDiffer /= laneAssists.size();
        killInvolvementRate /= laneAssists.size();
    }

    public void setLaneAssistByDataBefore_15(Before_15_Data before15Data) {
        this.killAssistDiffer = before15Data.getJgData().getMyKillCount()
                + before15Data.getJgData().getMyAssistCount()
                - before15Data.getJgData().getOppositeKillCount()
                - before15Data.getJgData().getOppositeAssistCount();
        this.killInvolvementRate = before15Data.getJgData().getMyTotalKillCount() != 0?
                before15Data.getJgData().getMyKillCount() / before15Data.getJgData().getMyTotalKillCount() : 0;
    }
}
