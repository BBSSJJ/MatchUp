package com.ssafy.matchup_statistics.global.exception;

public class RiotDataException extends RuntimeException {
    private RiotDataError riotDataError;

    public RiotDataException(RiotDataError riotDataError) {
        this.riotDataError = riotDataError;
    }
}
