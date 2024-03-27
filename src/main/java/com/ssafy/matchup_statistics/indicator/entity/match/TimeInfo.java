package com.ssafy.matchup_statistics.indicator.entity.match;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class TimeInfo {

    private long gameDuration;
    private long startTime;
    private long endTime;

    public TimeInfo(long gameDuration, long startTime, long endTime) {
        this.gameDuration = gameDuration;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
