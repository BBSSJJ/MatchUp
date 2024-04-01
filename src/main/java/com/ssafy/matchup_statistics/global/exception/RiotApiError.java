package com.ssafy.matchup_statistics.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RiotApiError {
    NO_RECENT_GAME_ERROR("최근 플레이한 게임이 없습니다."),
    NO_LEAGUE_ENTRY_ERROR("해당 페이지의 리그 엔트리를 찾을 수 없습니다."),
    NOT_IN_STATISTICS_DATABASE("통계 DB에 저장된 정보가 없습니다."),
    NOT_IN_RIOT_API("라이엇 API에 요청 정보가 없습니다.");
    private final String message;
}
