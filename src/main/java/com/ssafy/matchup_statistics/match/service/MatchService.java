package com.ssafy.matchup_statistics.match.service;

import com.ssafy.matchup_statistics.global.dto.response.MatchDetailResponseDto;
import com.ssafy.matchup_statistics.match.service.sub.MatchSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchService {
    private final MatchSaveService matchSaveService;

    public void save(MatchDetailResponseDto matchDetailResponseDto){
        matchSaveService.save(matchDetailResponseDto);
    }
}
