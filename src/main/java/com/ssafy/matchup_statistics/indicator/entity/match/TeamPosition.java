package com.ssafy.matchup_statistics.indicator.entity.match;

import com.ssafy.matchup_statistics.global.exception.RiotDataError;
import com.ssafy.matchup_statistics.global.exception.RiotDataException;

public enum TeamPosition {
    TOP, JUNGLE, MIDDLE, BOTTOM, UTILITY;

    public LaneType parseLaneType() {
        return switch (this) {
            case TOP -> LaneType.TOP_LANE;
            case JUNGLE -> throw new RiotDataException(RiotDataError.IMPOSSIBLE_TO_CHANGE_LANE_ERROR);
            case MIDDLE -> LaneType.MID_LANE;
            case BOTTOM -> LaneType.BOT_LANE;
            case UTILITY -> LaneType.BOT_LANE;
        };
    }
}
