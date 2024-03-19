package com.ssafy.matchup_statistics.indicator.service.builder.riot.match.end;

import com.ssafy.matchup_statistics.indicator.entity.riot.match.end.DetailMacroIndicator;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.end.MacroIndicator;
import com.ssafy.matchup_statistics.match.api.MatchRestApi;
import com.ssafy.matchup_statistics.match.api.dto.response.MatchDetailResponseDto;
import com.ssafy.matchup_statistics.match.api.dto.response.MatchTimelineResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MacroIndicatorBuilder {

    public DetailMacroIndicator build(MatchDetailResponseDto matchDto) {
        // TODO : matchDto로부터 macroIndicator 생성하기
        return null;
    }

    public void add(MacroIndicator macroIndicator, DetailMacroIndicator detailMacroIndicator) {
        // TODO : macroIndicator에 DetailMacroIndicator 추가하기
    }
}
