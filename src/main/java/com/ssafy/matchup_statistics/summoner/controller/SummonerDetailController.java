package com.ssafy.matchup_statistics.summoner.controller;

import com.ssafy.matchup_statistics.global.dto.response.MessageDto;
import com.ssafy.matchup_statistics.summoner.dto.response.SummonerDetailInfoResponseDto;
import com.ssafy.matchup_statistics.summoner.dto.response.SummonerLeagueInfoResponseDto;
import com.ssafy.matchup_statistics.summoner.dto.response.SummonerRecordInfoResponseDto;
import com.ssafy.matchup_statistics.summoner.service.SummonerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/statistics/summoners/details")
@Tag(name = "Summoner Details", description = "소환사의 상세페이지 조회 시 필요한 API입니다.")
public class SummonerDetailController {

    private final SummonerService summonerService;

    @Operation(summary = "소환사 상세정보 조회(User PK)", description = "사용자 pk로 상세정보를 불러오는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상세정보 응답",
                    content = @Content(schema = @Schema(implementation = SummonerDetailInfoResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = MessageDto.class)))
    })
    @GetMapping("/users/{userId}")
    public ResponseEntity<SummonerDetailInfoResponseDto> getSummonerInfoByUserId(
            @PathVariable(value = "userId") Long userId) {

        return ResponseEntity.ok(summonerService.getSummonerDetailInfo(userId));
    }

    @Operation(summary = "소환사 상세정보 조회(Game Name, Tag Name)", description = "게임 이름과 태그로 상세정보를 불러오는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상세정보 응답",
                    content = @Content(schema = @Schema(implementation = SummonerDetailInfoResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = MessageDto.class)))
    })
    @GetMapping("/riot-ids/{gameName}/tag-lines/{tagLine}")
    public ResponseEntity<SummonerDetailInfoResponseDto> getSummonerInfoByGameNameAndTag(
            @PathVariable(value = "gameName") String gameName,
            @PathVariable(value = "tagLine") String tagLine) {

        return ResponseEntity.ok(summonerService.getSummonerDetailInfo(gameName, tagLine));
    }
}
