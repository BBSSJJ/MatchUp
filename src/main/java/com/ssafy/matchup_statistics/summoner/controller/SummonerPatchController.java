package com.ssafy.matchup_statistics.summoner.controller;

import com.ssafy.matchup_statistics.global.dto.response.MessageDto;
import com.ssafy.matchup_statistics.league.dto.request.LeagueEntryRequestDto;
import com.ssafy.matchup_statistics.summoner.service.SummonerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/statistics/summoners/patch")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Summoner Patch", description = "소환사의 전적정보와 상세정보를 Riot API를 통해 업데이트합니다.")
public class SummonerPatchController {

    public SummonerService summonerService;

    @Operation(summary = "소환사 1명 리그정보, 통계지표, 매치정보 패치(Game Name, Tag Name) | 동기", description = "게임 이름과 태그로 모든 정보를 동기적으로 생성 및 저장하는 API 입니다.") // 해당 API가 어떤 역할을 하는지 설명
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "모든 정보 생성 및 저장완료", // 응답코드 200일때 응답 설명
                    content = @Content(schema = @Schema(implementation = MessageDto.class))), // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
            @ApiResponse(responseCode = "400", description = "잘못된 요청", // 응답코드 400일때 응답 설명
                    content = @Content(schema = @Schema(implementation = MessageDto.class))) // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
    })
    @PostMapping("/leagues/indicators/matches/riot-ids/{gameName}/tag-lines/{tagLine}/rest")
    public ResponseEntity<MessageDto> postSummonerInfoByGameNameAndTagRest(
            @PathVariable(value = "gameName") String gameName,
            @PathVariable(value = "tagLine") String tagLine) {
        summonerService.saveSummonerLeagueIndicatorMatchesRest(gameName, tagLine);
        return ResponseEntity.ok(new MessageDto("모든 정보 생성 및 저장완료"));
    }

    @Operation(summary = "리그 티어별 전체 소환사 리그정보, 통계지표, 매치정보 패치(by league & count) | 동기", description = "리그 티어에 해당하는 페이지의 모든 정보를 동기적으로 생성 및 저장하는 API 입니다.") // 해당 API가 어떤 역할을 하는지 설명
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "00개 정보 생성 및 저장완료", // 응답코드 200일때 응답 설명
                    content = @Content(schema = @Schema(implementation = MessageDto.class))), // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
            @ApiResponse(responseCode = "400", description = "잘못된 요청", // 응답코드 400일때 응답 설명
                    content = @Content(schema = @Schema(implementation = MessageDto.class))) // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
    })
    @PostMapping("/leagues/indicators/matches/league-entries/{pages}/rest")
    public ResponseEntity<MessageDto> postSummonerInfoByLeagueEntryRest(
            @PathVariable(value = "pages") @Valid @NonNull Integer pages,
            @RequestBody @Valid LeagueEntryRequestDto dto) {
        log.info(dto.getLeagueEntryRequestUrl());
        int createCount = summonerService.saveAllSummonerLeagueIndicatorMatchesRest(pages, dto);
        return ResponseEntity.ok(new MessageDto(createCount + "개 정보 생성 및 저장완료"));
    }

    @Operation(summary = "소환사 1명 리그정보, 통계지표, 매치정보 패치(Game Name, Tag Name) | 비동기", description = "게임 이름과 태그로 모든 정보를 비동기적으로 생성 및 저장하는 API 입니다.") // 해당 API가 어떤 역할을 하는지 설명
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "모든 정보 생성 및 저장완료", // 응답코드 200일때 응답 설명
                    content = @Content(schema = @Schema(implementation = MessageDto.class))), // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
            @ApiResponse(responseCode = "400", description = "잘못된 요청", // 응답코드 400일때 응답 설명
                    content = @Content(schema = @Schema(implementation = MessageDto.class))) // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
    })
    @PostMapping("/leagues/indicators/matches/riot-ids/{gameName}/tag-lines/{tagLine}/flux")
    public ResponseEntity<MessageDto> postSummonerInfoByGameNameAndTagFlux(
            @PathVariable(value = "gameName") String gameName,
            @PathVariable(value = "tagLine") String tagLine) {
        summonerService.saveSummonerLeagueIndicatorMatchesFlux(gameName, tagLine);
        return ResponseEntity.ok(new MessageDto("모든 정보 생성 및 저장완료"));
    }

    @Operation(summary = "리그 티어별 전체 소환사 리그정보, 통계지표, 매치정보 패치(by league & count) | 비동기", description = "리그 티어에 해당하는 페이지의 모든 정보를 비동기적으로 생성 및 저장하는 API 입니다.") // 해당 API가 어떤 역할을 하는지 설명
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "00개 정보 생성 및 저장완료", // 응답코드 200일때 응답 설명
                    content = @Content(schema = @Schema(implementation = MessageDto.class))), // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
            @ApiResponse(responseCode = "400", description = "잘못된 요청", // 응답코드 400일때 응답 설명
                    content = @Content(schema = @Schema(implementation = MessageDto.class))) // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
    })
    @PostMapping("/leagues/indicators/matches/league-entries/{pages}/flux")
    public ResponseEntity<MessageDto> postSummonerInfoByLeagueEntryFlux(
            @RequestBody @Valid LeagueEntryRequestDto dto) {
        log.info(dto.getLeagueEntryRequestUrl());
        int createCount = summonerService.saveAllSummonerLeagueIndicatorMatchesFlux(dto);
        return ResponseEntity.ok(new MessageDto(createCount + "개 정보 생성 및 저장완료"));
    }
}
