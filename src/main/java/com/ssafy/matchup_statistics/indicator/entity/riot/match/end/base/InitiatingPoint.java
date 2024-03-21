package com.ssafy.matchup_statistics.indicator.entity.riot.match.end.base;

import lombok.Data;

@Data
public class InitiatingPoint {
    private Double totalTimeCCingOthersPerTotalDamageTaken;
    private Double totalDamageTakenPerTeamTotalDamageTaken;
    private Double damageSelfMitigatedPerTotalDamageTaken;
}
