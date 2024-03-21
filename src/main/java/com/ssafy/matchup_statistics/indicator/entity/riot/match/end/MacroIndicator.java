package com.ssafy.matchup_statistics.indicator.entity.riot.match.end;

import com.ssafy.matchup_statistics.indicator.entity.riot.match.end.base.*;
import com.ssafy.matchup_statistics.match.api.dto.response.MatchDetailResponseDto;
import lombok.Data;

@Data
public class MacroIndicator {

    private SplitPoint splitPoint;
    private InitiatingPoint initiatingPoint;
    private JungleHoldPoint jungleHoldPoint;
    private ObjectivePoint objectivePoint;
    private VisionPoint visionPoint;
    private TotalDealPoint totalDealPoint;


    public MacroIndicator(MatchDetailResponseDto matchDetailResponseDto) {

    }
}
