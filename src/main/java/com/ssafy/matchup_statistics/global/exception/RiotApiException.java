package com.ssafy.matchup_statistics.global.exception;

public class RiotApiException extends RuntimeException{

    private RiotApiError riotApiError;

    public RiotApiException(RiotApiError riotApiError) {
        this.riotApiError = riotApiError;
    }
}
