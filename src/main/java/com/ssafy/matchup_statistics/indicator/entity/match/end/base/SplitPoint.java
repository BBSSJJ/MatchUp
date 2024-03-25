package com.ssafy.matchup_statistics.indicator.entity.match.end.base;

import com.ssafy.matchup_statistics.indicator.data.MacroData;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class SplitPoint {
    private long turretKillsPerDeaths;
    private long damageDealtToTurretsPerTotalDamageDealt;
    private long damageDealtToTurretsPerTeamTotalTowerDamageDone;

    public SplitPoint(MacroData macroData, int DEFAULT_ROUND_UP) {
        turretKillsPerDeaths =
                (long) macroData.getMyData().getTurretKills() * DEFAULT_ROUND_UP / (macroData.getMyData().getDeaths() + 1);
        damageDealtToTurretsPerTotalDamageDealt =
                (long) macroData.getMyData().getDamageDealtToTurrets() * DEFAULT_ROUND_UP / (macroData.getMyData().getTotalDamageDealt() + 1);
        damageDealtToTurretsPerTeamTotalTowerDamageDone =
                (long) macroData.getMyData().getDamageDealtToTurrets() * DEFAULT_ROUND_UP / (macroData.getTeamData().getTeamDamageDealtToTurrets() + 1);
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
