package com.ssafy.matchup_statistics.indicator.entity.match.end.base;

import com.ssafy.matchup_statistics.indicator.data.MacroData;
import lombok.Getter;

import java.util.List;

@Getter
public class SplitPoint {
    private final int DEFAULT_ROUND_UP = 100_000;
    private long turretKillsPerDeaths;
    private long damageDealtToTurretsPerTotalDamageDealt;
    private long damageDealtToTurretsPerTeamTotalTowerDamageDone;

    public SplitPoint(MacroData macroData) {
        turretKillsPerDeaths =
                (long) macroData.getMyData().getTurretKills() * DEFAULT_ROUND_UP / (macroData.getMyData().getDeaths() + 1);
        damageDealtToTurretsPerTotalDamageDealt =
                (long) macroData.getMyData().getDamageDealtToTurrets() * DEFAULT_ROUND_UP / macroData.getMyData().getTotalDamageDealt();
        damageDealtToTurretsPerTeamTotalTowerDamageDone =
                (long) macroData.getMyData().getDamageDealtToTurrets() * DEFAULT_ROUND_UP / macroData.getTeamData().getTeamDamageDealtToTurrets();
    }

    public SplitPoint(List<SplitPoint> splitPoints) {
        splitPoints.forEach(splitPoint -> {
            turretKillsPerDeaths += splitPoint.getTurretKillsPerDeaths();
            damageDealtToTurretsPerTotalDamageDealt += splitPoint.getDamageDealtToTurretsPerTotalDamageDealt();
            damageDealtToTurretsPerTeamTotalTowerDamageDone += splitPoint.getDamageDealtToTurretsPerTeamTotalTowerDamageDone();
        });
        turretKillsPerDeaths /= splitPoints.size();
        damageDealtToTurretsPerTotalDamageDealt /= splitPoints.size();
        damageDealtToTurretsPerTeamTotalTowerDamageDone /= splitPoints.size();
    }
}
