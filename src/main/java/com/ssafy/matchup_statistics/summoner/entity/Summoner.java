package com.ssafy.matchup_statistics.summoner.entity;

import com.ssafy.matchup_statistics.account.entity.Account;
import com.ssafy.matchup_statistics.league.entity.League;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "summoners")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Summoner {
    @Id
    private String id;
    private Account account;
    private SummonerDetail summonerDetail;
    private League league;
    private List<String> matchIds;
    private boolean indicatorExist;
}
