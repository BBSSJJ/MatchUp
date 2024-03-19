package com.ssafy.matchup_statistics.match.entity;

import com.ssafy.matchup_statistics.indicator.entity.riot.match.end.EtcIndicator;
//import com.ssafy.matchup_statistics.indicator.entity.riot.beginning.lane.*;
//import com.ssafy.matchup_statistics.indicator.entity.riot.lane.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "summoner")
public class Match {
    @Id
    private String id;
    private LineType lineType;
//    private TopIndicator topIndicator;
//    private JgIndicator jgIndicator;
//    private MidIndicator midIndicator;
//    private AdcIndicator adcIndicator;
//    private SupIndicator supIndicator;
//    private MacroIndicator macroIndicator;
    private EtcIndicator etcIndicator;
}

