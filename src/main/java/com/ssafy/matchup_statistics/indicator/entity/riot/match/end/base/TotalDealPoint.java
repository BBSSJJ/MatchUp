package com.ssafy.matchup_statistics.indicator.entity.riot.match.end.base;

import lombok.Data;

@Data
public class TotalDealPoint {
    private Double damagePerMinute;
    private Double dealPerGold;
    private Double teamDamagePercentage;
}
