package com.ssafy.matchup_statistics.match.dao;

import com.ssafy.matchup_statistics.global.dto.response.MatchDetailResponseDto;
import com.ssafy.matchup_statistics.match.entity.Match;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MatchDaoImpl {

    private final MongoTemplate mongoTemplate;
    public void save(MatchDetailResponseDto matchDetailResponseDto) {
        mongoTemplate.save(new Match(
                matchDetailResponseDto.getMetadata().getMatchId(),
                matchDetailResponseDto));
    }
}
