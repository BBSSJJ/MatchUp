package com.ssafy.matchup_statistics.indicator.service.builder.riot.match.end;

import com.ssafy.matchup_statistics.indicator.entity.riot.match.end.DetailEtcIndicator;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.end.EtcIndicator;
import com.ssafy.matchup_statistics.match.api.dto.response.MatchDetailResponseDto;
import org.springframework.stereotype.Component;

@Component
public class EtcIndicatorBuilder {

    public DetailEtcIndicator build(MatchDetailResponseDto matchDto) {
        // TODO : matchDto로부터 etcIndicator 생성하기
        return null;
    }
    public void add(EtcIndicator etcIndicator, DetailEtcIndicator detailEtcIndicator) {
        // TODO : etcIndicator에 DetailEtcIndicator 추가하기
    }
}
