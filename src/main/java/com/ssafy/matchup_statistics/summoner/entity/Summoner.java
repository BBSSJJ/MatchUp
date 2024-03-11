package com.ssafy.matchup_statistics.summoner.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "summoner")
@Builder
@AllArgsConstructor
@Getter
public class Summoner {
    @Id
    private String id;
    private String puuid;
    private String accountId;
    private String name;
    private Integer profileIconId;
    private Long revisionDate;
    private Long summonerLevel;
    private List<String> matchIds;
    private League league;

}
