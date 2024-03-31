package com.ssafy.matchup_statistics.match.entity;

import com.ssafy.matchup_statistics.global.dto.response.MatchDetailResponseDto;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "matches")
@Getter
public class Match {
    @Id
    private String id;
    private MatchDetailResponseDto matchDetail;

    public Match(String matchId, MatchDetailResponseDto matchDetail) {
        this.id = matchId;
        this.matchDetail = matchDetail;
    }
}

