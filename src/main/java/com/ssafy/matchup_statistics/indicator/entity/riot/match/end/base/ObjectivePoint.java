package com.ssafy.matchup_statistics.indicator.entity.riot.match.end.base;

import com.ssafy.matchup_statistics.indicator.entity.riot.match.end.MacroData;
import lombok.Data;

@Data
public class ObjectivePoint {

    private int getObjectiveDiffer;

    public ObjectivePoint(MacroData marcoTeamData) {
        getObjectiveDiffer = marcoTeamData.getTeamData().getMyTeamGetObjectives() - marcoTeamData.getTeamData().getOppositeTeamGetObjectives();
    }
}
