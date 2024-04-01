package com.ssafy.matchup_statistics.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RiotDataError {
    ILLEGAL_LANE_NUMBER_ERROR("잘못된 라인 번호입니다."),
    ILLEGAL_TEAM_POSITION_ERROR("잘못된 포지션입니다."),
    IMPOSSIBLE_TO_CHANGE_LANE_ERROR("해당 라인으로 변경이 불가능합니다."),
    NOT_IN_STATISTICS_DATABASE("통계 DB에 저장된 정보가 없습니다.");
    private final String message;
}
