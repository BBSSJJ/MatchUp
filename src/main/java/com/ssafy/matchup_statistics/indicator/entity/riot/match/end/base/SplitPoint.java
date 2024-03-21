package com.ssafy.matchup_statistics.indicator.entity.riot.match.end.base;

import com.ssafy.matchup_statistics.indicator.entity.riot.match.end.MacroData;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class SplitPoint {
    private int deathsDifferTurretKills;
    private long damageDealtToTurretsPerTotalDamageDealt;
    private long damageDealtToTurretsPerTeamTotalTowerDamageDone;
    private final int DEFAULT_ROUND_UP = 100_000;

    public SplitPoint(MacroData macroData) {
        deathsDifferTurretKills = macroData.getMyData().getDeaths() - macroData.getMyData().getTurretKills();
        damageDealtToTurretsPerTotalDamageDealt =
                (long) macroData.getMyData().getDamageDealtToTurrets() * DEFAULT_ROUND_UP / macroData.getMyData().getTotalDamageDealt();
        damageDealtToTurretsPerTeamTotalTowerDamageDone =
                (long) macroData.getMyData().getDamageDealtToTurrets() * DEFAULT_ROUND_UP / macroData.getTeamData().getTeamDamageDealtToTurrets();
    }
}
