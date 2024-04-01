package com.ssafy.matchup_statistics.match.entity;

import com.ssafy.matchup_statistics.global.dto.response.MatchDetailResponseDto;
import com.ssafy.matchup_statistics.summoner.dto.response.SummonerRecordInfoResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "matches")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Match {
    @Id
    private String id;
    private SummonerRecordInfoResponseDto.RecordMatchDetail matchDetail;
}

