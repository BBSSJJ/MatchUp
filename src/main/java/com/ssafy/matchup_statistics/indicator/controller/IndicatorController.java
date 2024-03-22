package com.ssafy.matchup_statistics.indicator.controller;

import com.ssafy.matchup_statistics.global.dto.response.MessageDto;
import com.ssafy.matchup_statistics.indicator.entity.SummonerIndicator;
import com.ssafy.matchup_statistics.indicator.service.IndicatorService;
import com.ssafy.matchup_statistics.summoner.dto.response.SummonerLeagueInfoResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/indicators/")
@RequiredArgsConstructor
@Tag(name = "Indicators", description = "통계 지표를 조회합니다.")
public class IndicatorController {

    private final IndicatorService indicatorService;

    @Operation(summary = "통계지표 조회(GameName, TagLine)", description = "게임 이름과 태그로 통계지표를 불러오는 API 입니다. DB에 없으면 라이엇 API를 통해 불러옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "통계지표", // 응답코드 200일때 응답 설명
                    content = @Content(schema = @Schema(implementation = SummonerLeagueInfoResponseDto.class))), // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
            @ApiResponse(responseCode = "400", description = "잘못된 요청", // 응답코드 400일때 응답 설명
                    content = @Content(schema = @Schema(implementation = MessageDto.class))) // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
    })
    @GetMapping("riot-ids/{gameName}/tag-lines/{tagLine}")
    public ResponseEntity<SummonerIndicator> getSummonerIndicatorByGameNameAndTag(
            @PathVariable(value = "gameName") String gameName,
            @PathVariable(value = "tagLine") String tagLine) {
        SummonerIndicator summonerIndicator = indicatorService.getSummonerIndicator(gameName, tagLine);
        return ResponseEntity.ok(summonerIndicator);
    }

    @Operation(summary = "통계지표 조회(Summoner Name)", description = "소환사명으로 통계지표를 불러오는 API 입니다. DB에 없으면 라이엇 API를 통해 불러옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "통계지표", // 응답코드 200일때 응답 설명
                    content = @Content(schema = @Schema(implementation = SummonerLeagueInfoResponseDto.class))), // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
            @ApiResponse(responseCode = "400", description = "잘못된 요청", // 응답코드 400일때 응답 설명
                    content = @Content(schema = @Schema(implementation = MessageDto.class))) // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
    })
    @GetMapping("summoner-names/{summonerName}")
    public ResponseEntity<SummonerIndicator> getSummonerIndicatorBySummonerName(
            @PathVariable(value = "summonerName") String summonerName) {
        SummonerIndicator summonerIndicator = indicatorService.getSummonerIndicatorBySummonerName(summonerName);
        return ResponseEntity.ok(summonerIndicator);
    }

    @Operation(summary = "통계지표 조회(PUUID)", description = "PUUID로 통계지표를 불러오는 API 입니다. DB에 없으면 라이엇 API를 통해 불러옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "통계지표", // 응답코드 200일때 응답 설명
                    content = @Content(schema = @Schema(implementation = SummonerLeagueInfoResponseDto.class))), // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
            @ApiResponse(responseCode = "400", description = "잘못된 요청", // 응답코드 400일때 응답 설명
                    content = @Content(schema = @Schema(implementation = MessageDto.class))) // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
    })
    @GetMapping("puuids/{puuid}")
    public ResponseEntity<SummonerIndicator> getSummonerIndicatorByPuuid(
            @PathVariable(value = "puuid") String puuid) {
        SummonerIndicator summonerIndicator = indicatorService.getSummonerIndicator(puuid);
        return ResponseEntity.ok(summonerIndicator);
    }
}
