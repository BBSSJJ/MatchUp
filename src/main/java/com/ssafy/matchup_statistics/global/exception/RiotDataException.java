package com.ssafy.matchup_statistics.global.exception;

import lombok.Getter;

@Getter
public class RiotDataException extends RuntimeException {
    private RiotDataError riotDataError;
    private String message;


    public RiotDataException(RiotDataError riotDataError) {
        this.message = riotDataError.getMessage();
        this.riotDataError = riotDataError;
    }
}
