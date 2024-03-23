package com.ssafy.matchup_statistics.match.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "matches")
@Getter
public class Match {
    @Id
    private String id;
    private MatchDetail matchDetail;

    public Match(String matchId, MatchDetail matchDetail) {
        this.id = matchId;
        this.matchDetail = matchDetail;
    }
}

