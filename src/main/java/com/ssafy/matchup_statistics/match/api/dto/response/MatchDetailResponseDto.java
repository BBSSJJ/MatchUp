package com.ssafy.matchup_statistics.match.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class MatchDetailResponseDto {
    @JsonProperty("metadata")
    MetadataDto metadataDto;
    @JsonProperty("info")
    InfoDto infoDto;

    @Data
    public static class MetadataDto {
        @JsonProperty("dataVersion")
        String dataVersion;
        @JsonProperty("matchId")
        String matchId;
    }

    @Data
    public static class InfoDto {
        @JsonProperty("participants")
        List<ParticipantDto> participants;

        @Data
        public static class ParticipantDto {
            @JsonProperty("kills")
            Integer kills;
            @JsonProperty("deaths")
            Integer deaths;
            @JsonProperty("assists")
            Integer assists;
            @JsonProperty("championName")
            String championName;
            @JsonProperty("individualPosition")
            String individualPosition;
        }
    }
}
