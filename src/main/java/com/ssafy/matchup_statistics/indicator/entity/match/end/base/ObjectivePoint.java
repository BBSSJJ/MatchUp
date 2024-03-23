package com.ssafy.matchup_statistics.indicator.entity.match.end.base;

import com.ssafy.matchup_statistics.indicator.data.MacroData;
import lombok.Getter;

import java.util.List;

@Getter
public class ObjectivePoint {

    private long getObjectiveDifferPerGameDuration;
    private final int DEFAULT_ROUND_UP = 100_000;

    public ObjectivePoint(MacroData marcoTeamData) {
        getObjectiveDifferPerGameDuration = (long) (marcoTeamData.getTeamData().getMyTeamGetObjectives() - marcoTeamData.getTeamData().getOppositeTeamGetObjectives())
                * DEFAULT_ROUND_UP / marcoTeamData.getGameDuration();
    }

    public ObjectivePoint(List<ObjectivePoint> objectivePoints) {
        objectivePoints.forEach(objectivePoint -> {
            getObjectiveDifferPerGameDuration += objectivePoint.getGetObjectiveDifferPerGameDuration();
        });
        getObjectiveDifferPerGameDuration /= objectivePoints.size();
    }
}
