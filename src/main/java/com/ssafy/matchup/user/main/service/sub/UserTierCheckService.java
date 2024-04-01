package com.ssafy.matchup.user.main.service.sub;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserTierCheckService {
    public List<Pair<String, String>> getTierList(String tier, String leagueRank) {
        int cur = TierClass.valueOf(tier + "_" + leagueRank).ordinal();
        List<Pair<String, String>> list = new ArrayList<>();
        if ("DIAMOND".equals(tier)) {
            for (int i = cur - 2; i <= cur + 2; i++) {
                if (i > TierClass.DIAMOND_I.ordinal()) break;
                String tierDivision = TierClass.values()[i].toString();
                String[] parts = tierDivision.split("_");
                list.add(Pair.of(parts[0], parts[1]));
            }
        } else if ("EMERALD".equals(tier)) {
            int low = ((cur - 4) / 4) * 4;
            for (int i = low; i < low + 8; i++) {
                String tierDivision = TierClass.values()[i].toString();
                String[] parts = tierDivision.split("_");
                list.add(Pair.of(parts[0], parts[1]));
            }
            if (cur + 2 >= TierClass.DIAMOND_IV.ordinal()) {
                for (int i = TierClass.DIAMOND_IV.ordinal(); i <= cur + 2; i++) {
                    String tierDivision = TierClass.values()[i].toString();
                    String[] parts = tierDivision.split("_");
                    list.add(Pair.of(parts[0], parts[1]));
                }
            }

        } else if ("IRON".equals(tier)) {
            for (int i = 0; i < 8; i++) {
                String tierDivision = TierClass.values()[i].toString();
                String[] parts = tierDivision.split("_");
                list.add(Pair.of(parts[0], parts[1]));
            }
        } else {
            int low = ((cur - 4) / 4) * 4;
            for (int i = low; i < low + 12; i++) {
                String tierDivision = TierClass.values()[i].toString();
                String[] parts = tierDivision.split("_");
                list.add(Pair.of(parts[0], parts[1]));
            }
        }
        return list;
    }

    public enum TierClass {
        IRON_IV, IRON_III, IRON_II, IRON_I,
        BRONZE_IV, BRONZE_III, BRONZE_II, BRONZE_I,
        SILVER_IV, SILVER_III, SILVER_II, SILVER_I,
        GOLD_IV, GOLD_III, GOLD_II, GOLD_I,
        PLATINUM_IV, PLATINUM_III, PLATINUM_II, PLATINUM_I,
        EMERALD_IV, EMERALD_III, EMERALD_II, EMERALD_I,
        DIAMOND_IV, DIAMOND_III, DIAMOND_II, DIAMOND_I
    }
}


