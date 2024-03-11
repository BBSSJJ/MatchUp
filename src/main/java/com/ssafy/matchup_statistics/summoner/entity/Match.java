package com.ssafy.matchup_statistics.summoner.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "summoner")
public class Match {
    @Id
    private String id;
}

