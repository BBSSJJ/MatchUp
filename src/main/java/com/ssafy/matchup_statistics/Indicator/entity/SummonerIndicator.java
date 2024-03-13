package com.ssafy.matchup_statistics.Indicator.entity;

import com.ssafy.matchup_statistics.Indicator.entity.riot.additional.EtcIndicator;
import com.ssafy.matchup_statistics.Indicator.entity.riot.additional.MacroIndicator;
import com.ssafy.matchup_statistics.Indicator.entity.riot.line.*;
import com.ssafy.matchup_statistics.Indicator.entity.user.UserIndicator;
import com.ssafy.matchup_statistics.match.entity.Match;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "summoner_indicators")
public class SummonerIndicator {
    @Id
    private Long summonerId;
    private List<Match> calculatedTopMatches;
    private TopIndicator totalTopIndicator;
    private List<Match> calculatedJgMatches;
    private JgIndicator totalJgIndicator;
    private List<Match> calculatedMidMatches;
    private MidIndicator totalMidIndicator;
    private List<Match> calculatedAdcMatches;
    private AdcIndicator totalAdcIndicator;
    private List<Match> calculatedSupMatches;
    private SupIndicator totalSupIndicator;
    private List<Match> calculatedMacroMatches;
    private MacroIndicator totalMacroIndicator;
    private List<Match> calculatedEtcMatches;
    private EtcIndicator totalEtcIndicator;
    private UserIndicator userIndicator;
}
