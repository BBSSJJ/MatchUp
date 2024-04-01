package com.ssafy.matchup_statistics.global.dto;

import com.ssafy.matchup_statistics.global.dto.response.LeagueInfoResponseDto;
import lombok.Data;

import java.util.List;

@Data
public class HighTierResponseDto {
    private String tier;
    private String leagueId;
    private String queue;
    private String name;
    private List<LeagueInfoResponseDto> entries;
}
