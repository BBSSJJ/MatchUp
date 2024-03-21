package com.ssafy.matchup_statistics.indicator.entity.riot.match.end.base;

import com.ssafy.matchup_statistics.indicator.entity.riot.match.end.MacroData;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class InitiatingPoint {
    private long totalTimeCCingOthersPerTotalDamageTaken;
    private long totalDamageTakenPerTeamTotalDamageTaken;
    private long damageSelfMitigatedPerTotalDamageTaken;
    private final int DEFAULT_ROUND_UP = 100_000;

    public InitiatingPoint(MacroData macroData) {
        totalTimeCCingOthersPerTotalDamageTaken =
                (long) macroData.getMyData().getTimeCCingOthers() * DEFAULT_ROUND_UP / macroData.getMyData().getTotalDamageTaken();
        totalDamageTakenPerTeamTotalDamageTaken =
                (long) macroData.getMyData().getTotalDamageTaken() * DEFAULT_ROUND_UP / macroData.getTeamData().getTeamTotalDamageTaken();
        damageSelfMitigatedPerTotalDamageTaken =
                (long) macroData.getMyData().getDamageSelfMitigated() * DEFAULT_ROUND_UP / macroData.getMyData().getTotalDamageTaken();
    }
}
