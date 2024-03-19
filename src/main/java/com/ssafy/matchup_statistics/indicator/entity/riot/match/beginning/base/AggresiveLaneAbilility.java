package com.ssafy.matchup_statistics.indicator.entity.riot.match.beginning.base;

import lombok.Data;

@Data
public class AggresiveLaneAbilility {
    private Long dealDiffer;
    private Long soloKillDiffer;
    private Long killInvolvementInEnemyCamp;
    private Long duoKillAndSoloKillDiffer;
}
