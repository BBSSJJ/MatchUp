package com.ssafy.matchup_statistics.global.exception;

import lombok.Getter;

@Getter
public class RiotApiException extends RuntimeException{

    private RiotApiError riotApiError;
    private String message;

    public RiotApiException(RiotApiError riotApiError) {
        this.message = riotApiError.getMessage();
        this.riotApiError = riotApiError;
    }
}
