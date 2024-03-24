package com.ssafy.matchup_statistics.match.service.sub;

import com.ssafy.matchup_statistics.global.dto.response.MatchDetailResponseDto;
import com.ssafy.matchup_statistics.global.util.MongoTemplateAdaptor;
import com.ssafy.matchup_statistics.global.util.mapper.MatchMapper;
import com.ssafy.matchup_statistics.match.entity.Match;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MatchSaveService {

    private final MongoTemplateAdaptor mongoTemplateAdaptor;
    private final MatchMapper matchMapper;
    public void save(MatchDetailResponseDto matchDetailResponseDto) {
        mongoTemplateAdaptor.saveMatch(new Match(
                matchDetailResponseDto.getMetadata().getMatchId(),
                matchMapper.matchDetailResponseDtoToMatchDetail(matchDetailResponseDto)));
    }
}
