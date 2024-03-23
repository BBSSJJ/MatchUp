package com.ssafy.matchup_statistics.indicator.controller;

import com.ssafy.matchup_statistics.global.dto.response.MessageDto;
import com.ssafy.matchup_statistics.indicator.dto.response.IndicatorResponseDto;
import com.ssafy.matchup_statistics.league.dto.request.LeagueEntryRequestDto;
import com.ssafy.matchup_statistics.indicator.entity.Indicator;
import com.ssafy.matchup_statistics.indicator.service.IndicatorService;
import com.ssafy.matchup_statistics.summoner.dto.response.SummonerLeagueInfoResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/indicators/")
@RequiredArgsConstructor
@Tag(name = "Indicators", description = "통계 지표를 조회 및 생성합니다.")
public class IndicatorController {

    private final IndicatorService indicatorService;

    @Operation(summary = "통계지표 조회(GameName, TagLine)", description = "게임 이름과 태그로 통계지표를 불러오는 API 입니다. Riot API를 통해 불러오며 DB에 저장되지 않습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "통계지표", // 응답코드 200일때 응답 설명
                    content = @Content(schema = @Schema(implementation = IndicatorResponseDto.class))), // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
            @ApiResponse(responseCode = "400", description = "잘못된 요청", // 응답코드 400일때 응답 설명
                    content = @Content(schema = @Schema(implementation = MessageDto.class))) // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
    })
    @GetMapping("riot-ids/{gameName}/tag-lines/{tagLine}")
    public ResponseEntity<IndicatorResponseDto> getSummonerIndicatorByGameNameAndTag(
            @PathVariable(value = "gameName") String gameName,
            @PathVariable(value = "tagLine") String tagLine) {
        return ResponseEntity.ok(indicatorService.getSummonerIndicator(gameName, tagLine));
    }

    @Operation(summary = "통계지표 조회(Summoner Name)", description = "소환사명으로 통계지표를 불러오는 API 입니다. Riot API를 통해 불러오며 DB에 저장되지 않습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "통계지표", // 응답코드 200일때 응답 설명
                    content = @Content(schema = @Schema(implementation = IndicatorResponseDto.class))), // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
            @ApiResponse(responseCode = "400", description = "잘못된 요청", // 응답코드 400일때 응답 설명
                    content = @Content(schema = @Schema(implementation = MessageDto.class))) // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
    })
    @GetMapping("summoner-names/{summonerName}")
    public ResponseEntity<IndicatorResponseDto> getSummonerIndicatorBySummonerName(
            @PathVariable(value = "summonerName") String summonerName) {
        return ResponseEntity.ok(indicatorService.getSummonerIndicatorBySummonerName(summonerName));
    }


    @Operation(summary = "통계지표 조회(PUUID)", description = "PUUID로 통계지표를 불러오는 API 입니다. Riot API를 통해 불러오며 DB에 저장되지 않습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "통계지표", // 응답코드 200일때 응답 설명
                    content = @Content(schema = @Schema(implementation = IndicatorResponseDto.class))), // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
            @ApiResponse(responseCode = "400", description = "잘못된 요청", // 응답코드 400일때 응답 설명
                    content = @Content(schema = @Schema(implementation = MessageDto.class))) // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
    })
    @GetMapping("puuids/{puuid}")
    public ResponseEntity<IndicatorResponseDto> getSummonerIndicatorByPuuid(
            @PathVariable(value = "puuid") String puuid) {
        return ResponseEntity.ok(indicatorService.getSummonerIndicator(puuid));
    }

    @Operation(summary = "통계지표 생성(tier)", description = "티어별 요청한 개수만큼 통계지표를 생성하여 DB에 저장합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "통계지표 생성 및 저장완료", // 응답코드 200일때 응답 설명
                    content = @Content(schema = @Schema(implementation = MessageDto.class))), // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
            @ApiResponse(responseCode = "400", description = "잘못된 요청", // 응답코드 400일때 응답 설명
                    content = @Content(schema = @Schema(implementation = MessageDto.class))), // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
            @ApiResponse(responseCode = "500", description = "서버 오류(메시지는 에러메시지 그대로 전송)", // 응답코드 400일때 응답 설명
                    content = @Content(schema = @Schema(implementation = MessageDto.class))) // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
    })

    @PostMapping("/league-entries/{pages}")
    public ResponseEntity<MessageDto> postNewSummonerIndicators(
            @PathVariable(value = "pages") @Valid @NonNull Integer pages,
            @RequestBody @Valid LeagueEntryRequestDto dto) {
        int createCount = indicatorService.buildNewIndicatorsByLeagueEntry(pages, dto);
        return ResponseEntity.ok(new MessageDto(createCount + "개 정상 생성완료"));
    }

    @Operation(summary = "통계지표 생성(GameName, TagLine)", description = "게임 이름과 태그로 통계지표를 생성하여 DB에 저장합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "통계지표 정상 생성완료", // 응답코드 200일때 응답 설명
                    content = @Content(schema = @Schema(implementation = MessageDto.class))), // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
            @ApiResponse(responseCode = "400", description = "잘못된 요청", // 응답코드 400일때 응답 설명
                    content = @Content(schema = @Schema(implementation = MessageDto.class))) // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
    })
    @PostMapping("riot-ids/{gameName}/tag-lines/{tagLine}")
    public ResponseEntity<MessageDto> postSummonerIndicatorByGameNameAndTag(
            @PathVariable(value = "gameName") String gameName,
            @PathVariable(value = "tagLine") String tagLine) {
        indicatorService.buildNewIndicatorByGameName(gameName, tagLine);
        return ResponseEntity.ok(new MessageDto("통계지표 정상 생성완료"));
    }
}
