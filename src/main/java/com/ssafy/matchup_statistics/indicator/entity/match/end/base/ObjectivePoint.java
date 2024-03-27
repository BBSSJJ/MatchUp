package com.ssafy.matchup_statistics.indicator.entity.match.end.base;

import com.ssafy.matchup_statistics.indicator.data.MacroData;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ObjectivePoint {

    private long getObjectiveDifferPerGameDuration;

    public ObjectivePoint(MacroData marcoTeamData, int DEFAULT_ROUND_UP) {
        getObjectiveDifferPerGameDuration = (long) (marcoTeamData.getTeamData().getMyTeamGetObjectives() - marcoTeamData.getTeamData().getOppositeTeamGetObjectives())
                * DEFAULT_ROUND_UP / (marcoTeamData.getTimeInfo().getGameDuration() + 1);
    }

    public ObjectivePoint(List<ObjectivePoint> objectivePoints) {
        objectivePoints.forEach(objectivePoint -> {
            getObjectiveDifferPerGameDuration += objectivePoint.getGetObjectiveDifferPerGameDuration();
        });
        getObjectiveDifferPerGameDuration /= objectivePoints.size();
    }
}
