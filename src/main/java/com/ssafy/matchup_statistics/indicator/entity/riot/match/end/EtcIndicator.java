package com.ssafy.matchup_statistics.indicator.entity.riot.match.end;

import com.ssafy.matchup_statistics.match.api.dto.response.MatchDetailResponseDto;
import com.ssafy.matchup_statistics.match.entity.Match;

import java.util.List;

public class EtcIndicator {
    private DetailEtcIndicator detailEtcIndicator;
    private List<String> calculatedMatchIds;
    public EtcIndicator (MatchDetailResponseDto matchDetailResponseDto){};
}
