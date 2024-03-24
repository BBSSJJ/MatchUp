package com.ssafy.matchup_statistics.indicator.entity.match.end.base;

import com.ssafy.matchup_statistics.indicator.data.MacroData;
import lombok.Getter;

import java.util.List;

@Getter
public class InitiatingPoint {
    private long totalTimeCCingOthersPerTotalDamageTaken;
    private long totalDamageTakenPerTeamTotalDamageTaken;
    private long damageSelfMitigatedPerTotalDamageTaken;

    public InitiatingPoint(MacroData macroData, int DEFAULT_ROUND_UP) {
        totalTimeCCingOthersPerTotalDamageTaken =
                (long) macroData.getMyData().getTimeCCingOthers() * DEFAULT_ROUND_UP / (macroData.getMyData().getTotalDamageTaken() + 1);
        totalDamageTakenPerTeamTotalDamageTaken =
                (long) macroData.getMyData().getTotalDamageTaken() * DEFAULT_ROUND_UP / macroData.getTeamData().getTeamTotalDamageTaken();
        damageSelfMitigatedPerTotalDamageTaken =
                (long) macroData.getMyData().getDamageSelfMitigated() * DEFAULT_ROUND_UP / (macroData.getMyData().getTotalDamageTaken() + 1);
    }

    public InitiatingPoint(List<InitiatingPoint> initiatingPoints) {
        if (initiatingPoints.isEmpty()) return;

        initiatingPoints.forEach(initiatingPoint -> {
            totalTimeCCingOthersPerTotalDamageTaken += initiatingPoint.getTotalTimeCCingOthersPerTotalDamageTaken();
            totalDamageTakenPerTeamTotalDamageTaken += initiatingPoint.getTotalDamageTakenPerTeamTotalDamageTaken();
            damageSelfMitigatedPerTotalDamageTaken += initiatingPoint.getDamageSelfMitigatedPerTotalDamageTaken();
        });
        totalTimeCCingOthersPerTotalDamageTaken /= initiatingPoints.size();
        totalDamageTakenPerTeamTotalDamageTaken /= initiatingPoints.size();
        damageSelfMitigatedPerTotalDamageTaken /= initiatingPoints.size();
    }
}
