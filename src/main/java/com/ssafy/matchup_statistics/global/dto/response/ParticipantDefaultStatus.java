package com.ssafy.matchup_statistics.global.dto.response;

import lombok.Data;

@Data
public abstract class ParticipantDefaultStatus {
    private Long goldPerSecond;
    private Long jungleMinionsKilled;
    private Long level;
    private Long minionsKilled;
    private Long participantId;
    private Long timeEnemySpentControlled;
    private Long totalGold;
    private Long xp;
}
