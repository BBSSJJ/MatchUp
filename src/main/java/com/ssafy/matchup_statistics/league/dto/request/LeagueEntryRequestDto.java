package com.ssafy.matchup_statistics.league.dto.request;

import com.ssafy.matchup_statistics.league.type.Queue;
import com.ssafy.matchup_statistics.league.type.Tier;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LeagueEntryRequestDto {
    @NotBlank
    private Integer division;
    @NotBlank
    private Tier tier;
    @NotBlank
    private Queue queue;
}
