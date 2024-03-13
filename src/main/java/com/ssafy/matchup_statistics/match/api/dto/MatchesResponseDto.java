package com.ssafy.matchup_statistics.match.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

import java.util.List;

@Data
public class MatchesResponseDto {
    @JsonUnwrapped
    List<String> Matches;
}
