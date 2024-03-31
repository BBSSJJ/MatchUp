package com.ssafy.matchup_statistics.match.service.sub;

import com.ssafy.matchup_statistics.global.dto.response.MatchDetailResponseDto;
import com.ssafy.matchup_statistics.global.util.MongoTemplateAdaptor;
import com.ssafy.matchup_statistics.match.entity.Match;
import com.ssafy.matchup_statistics.match.entity.MatchDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MatchSaveService {

    private final MongoTemplateAdaptor mongoTemplateAdaptor;
    public void save(MatchDetailResponseDto matchDetailResponseDto) {
        mongoTemplateAdaptor.saveMatch(new Match(
                matchDetailResponseDto.getMetadata().getMatchId(),
                matchDetailResponseDto));
    }
}
