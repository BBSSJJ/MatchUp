package com.ssafy.matchup_statistics.match.api.dto.response;

import lombok.Data;

@Data
public class DamageStats {
    private Long magicDamageDone;
    private Long magicDamageDoneToChampions;
    private Long magicDamageTaken;
    private Long physicalDamageDone;
    private Long physicalDamageDoneToChampions;
    private Long physicalDamageTaken;
    private Long totalDamageDone;
    private Long totalDamageDoneToChampions;
    private Long totalDamageTaken;
    private Long trueDamageDone;
    private Long trueDamageDoneToChampions;
    private Long trueDamageTaken;
}
