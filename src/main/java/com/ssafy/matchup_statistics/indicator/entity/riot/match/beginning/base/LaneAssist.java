package com.ssafy.matchup_statistics.indicator.entity.riot.match.beginning.base;

import lombok.Data;

@Data
public class LaneAssist {
    private int killAssistDiffer;
    private int killInvolvementRate;

    public void setLaneAssistByDataBefore_15(Before_15_Data before15Data) {
        this.killAssistDiffer = before15Data.getJgData().getMyKillCount()
                + before15Data.getJgData().getMyAssistCount()
                - before15Data.getJgData().getOppositeKillCount()
                - before15Data.getJgData().getOppositeAssistCount();
        this.killInvolvementRate = before15Data.getJgData().getMyTotalKillCount() != 0?
                before15Data.getJgData().getMyKillCount() / before15Data.getJgData().getMyTotalKillCount() : 0;
    }
}
