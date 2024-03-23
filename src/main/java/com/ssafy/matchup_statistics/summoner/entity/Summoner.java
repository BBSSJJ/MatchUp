package com.ssafy.matchup_statistics.summoner.entity;

import com.ssafy.matchup_statistics.account.entity.Account;
import com.ssafy.matchup_statistics.indicator.entity.Indicator;
import com.ssafy.matchup_statistics.league.entity.LeagueInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "summoners")
@Builder
@AllArgsConstructor
@Getter
public class Summoner {
    @Id
    private String id;
    private Account account;
    private SummonerInfo summonerInfo;
    private LeagueInfo leagueInfo;
    private List<String> matchIds;
    private String indicatorId;
}
