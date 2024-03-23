package com.ssafy.matchup_statistics.match.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "matches")
public class Match {
    @Id
    private String id;
    private MatchDetail matchDetail;
}

