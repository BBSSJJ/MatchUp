package com.ssafy.matchup_statistics.global.dto.response;

import lombok.Data;

@Data
public class SummonerInfoResponseDto {
    private String id;
    private String puuid;
    private String accountId;
    private String name;
    private Integer profileIconId;
    private Long revisionDate;
    private Long summonerLevel;
}
