package com.ssafy.matchup_statistics.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RiotApiError {
    NO_LEAGUE_ENTRY_ERROR("해당 페이지의 리그 엔트리를 찾을 수 없습니다.");
    private final String message;
}
