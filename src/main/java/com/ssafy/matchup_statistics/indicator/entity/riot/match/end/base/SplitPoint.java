package com.ssafy.matchup_statistics.indicator.entity.riot.match.end.base;

import lombok.Data;

@Data
public class SplitPoint {
    private Integer deathsDifferTurretKills;
    private Double damageDealtToTurretsPerTotalDamageDealt;
    private Double damageDealtToTurretsPerTeamTotalTowerDamageDone;
}
