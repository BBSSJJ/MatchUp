package com.ssafy.matchup_statistics.summoner.controller;

import com.ssafy.matchup_statistics.global.dto.response.MatchDetailResponseDto;
import com.ssafy.matchup_statistics.global.dto.response.MessageDto;
import com.ssafy.matchup_statistics.league.dto.request.LeagueEntryRequestDto;
import com.ssafy.matchup_statistics.summoner.dto.response.SummonerLeagueAccountInfoResponseDto;
import com.ssafy.matchup_statistics.summoner.dto.response.SummonerLeagueInfoResponseDto;
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

import java.util.List;

@RestController
@RequestMapping("/api/summoners")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Summoner", description = "소환사 정보를 조회합니다.")
public class SummonerController {

    private final SummonerService summonerService;

    @Operation(summary = "소환사 리그정보 조회(Game Name, Tag Name)", description = "게임 이름과 태그로 소환사정보와 리그정보를 불러오는 API 입니다.") // 해당 API가 어떤 역할을 하는지 설명
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소환사 + 리그정보", // 응답코드 200일때 응답 설명
                    content = @Content(schema = @Schema(implementation = SummonerLeagueInfoResponseDto.class))), // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
            @ApiResponse(responseCode = "400", description = "잘못된 요청", // 응답코드 400일때 응답 설명
                    content = @Content(schema = @Schema(implementation = MessageDto.class))) // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
    })
    @GetMapping("/leagues/riot-ids/{gameName}/tag-lines/{tagLine}")
    public ResponseEntity<SummonerLeagueInfoResponseDto> getSummonerInfoByGameNameAndTag(
            @PathVariable(value = "gameName") String gameName,
            @PathVariable(value = "tagLine") String tagLine) {
        SummonerLeagueInfoResponseDto summonerLeagueInfo = summonerService.getSummonerLeagueInfo(gameName, tagLine);
        return ResponseEntity.ok(summonerLeagueInfo);
    }

    @Operation(summary = "소환사 리그정보 조회(Summoner Name)", description = "소환사 이름으로 소환사정보와 리그정보를 불러오는 API 입니다.") // 해당 API가 어떤 역할을 하는지 설명
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소환사 + 리그정보", // 응답코드 200일때 응답 설명
                    content = @Content(schema = @Schema(implementation = SummonerLeagueInfoResponseDto.class))), // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
            @ApiResponse(responseCode = "400", description = "잘못된 요청", // 응답코드 400일때 응답 설명
                    content = @Content(schema = @Schema(implementation = MessageDto.class))) // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
    })
    @GetMapping("/leagues/summoner-names/{summonerName}")
    public ResponseEntity<SummonerLeagueInfoResponseDto> getSummonerInfoBySummonerName(
            @PathVariable(value = "summonerName") String summonerName) {
        SummonerLeagueInfoResponseDto summonerLeagueInfo = summonerService.getSummonerLeagueInfoBySummonerName(summonerName);
        return ResponseEntity.ok(summonerLeagueInfo);
    }

    @Operation(summary = "소환사 리그정보 조회(PUUID)", description = "PUUID 로 소환사정보와 리그정보를 불러오는 API 입니다.") // 해당 API가 어떤 역할을 하는지 설명
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소환사 + 리그정보", // 응답코드 200일때 응답 설명
                    content = @Content(schema = @Schema(implementation = SummonerLeagueInfoResponseDto.class))), // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
            @ApiResponse(responseCode = "400", description = "잘못된 요청", // 응답코드 400일때 응답 설명
                    content = @Content(schema = @Schema(implementation = MessageDto.class))) // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
    })
    @GetMapping("/leagues/puuids/{puuid}")
    public ResponseEntity<SummonerLeagueInfoResponseDto> getSummonerInfoByPuuid(
            @PathVariable(value = "puuid") String puuid) {
        SummonerLeagueInfoResponseDto summonerLeagueInfo = summonerService.getSummonerLeagueInfo(puuid);
        return ResponseEntity.ok(summonerLeagueInfo);
    }

    @Operation(summary = "소환사 1명 리그정보, 통계지표, 매치정보 생성(Game Name, Tag Name) | 동기", description = "게임 이름과 태그로 모든 정보를 동기적으로 생성 및 저장하는 API 입니다.") // 해당 API가 어떤 역할을 하는지 설명
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

    @Operation(summary = "리그 티어별 전체 소환사 리그정보, 통계지표, 매치정보 생성(by tag & count) | 동기", description = "리그 티어에 해당하는 페이지의 모든 정보를 동기적으로 생성 및 저장하는 API 입니다.") // 해당 API가 어떤 역할을 하는지 설명
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

    @Operation(summary = "소환사 1명 리그정보, 통계지표, 매치정보 생성(Game Name, Tag Name) | 비동기", description = "게임 이름과 태그로 모든 정보를 비동기적으로 생성 및 저장하는 API 입니다.") // 해당 API가 어떤 역할을 하는지 설명
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

    @Operation(summary = "리그 티어별 전체 소환사 리그정보, 통계지표, 매치정보 생성(by tag & count) | 비동기", description = "리그 티어에 해당하는 페이지의 모든 정보를 비동기적으로 생성 및 저장하는 API 입니다.") // 해당 API가 어떤 역할을 하는지 설명
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "00개 정보 생성 및 저장완료", // 응답코드 200일때 응답 설명
                    content = @Content(schema = @Schema(implementation = MessageDto.class))), // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
            @ApiResponse(responseCode = "400", description = "잘못된 요청", // 응답코드 400일때 응답 설명
                    content = @Content(schema = @Schema(implementation = MessageDto.class))) // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
    })
    @PostMapping("/leagues/indicators/matches/league-entries/flux")
    public ResponseEntity<MessageDto> postSummonerInfoByLeagueEntryFlux(
            @RequestBody @Valid LeagueEntryRequestDto dto) {
        int createCount = summonerService.saveAllSummonerLeagueIndicatorMatchesFlux(dto);
        return ResponseEntity.ok(new MessageDto(createCount + "개 정보 생성 및 저장완료"));
    }

    @Operation(summary = "상위티어 리그정보, 통계지표, 매치정보 생성", description = "마스터, 그랜드마스터, 챌린저 리그 저장 API") // 해당 API가 어떤 역할을 하는지 설명
    @PostMapping("/leagues/high-tiers/{tier}/indicators/matches/league-entries/flux")
    public ResponseEntity<MessageDto> postSummonerInfoByChallengerLeagueEntryFlux(
            @PathVariable String tier) {
        int createCount = summonerService.saveHighTierSummonerLeagueIndicatorMatchesFlux(tier);
        return ResponseEntity.ok(new MessageDto(createCount + "개 정보 생성 및 저장완료"));
    }

    @Operation(summary = "리그로 사용자 목록 조회 후 각각의 정보 전달(User Dump용)", description = "리그 티어에 해당하는 페이지의 모든 정보를 전송하는 API 입니다.") // 해당 API가 어떤 역할을 하는지 설명
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 정보 전송완료(리스트로 205개 반환)", // 응답코드 200일때 응답 설명
                    content = @Content(schema = @Schema(implementation = SummonerLeagueAccountInfoResponseDto.class))), // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
            @ApiResponse(responseCode = "400", description = "잘못된 요청", // 응답코드 400일때 응답 설명
                    content = @Content(schema = @Schema(implementation = MessageDto.class))) // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
    })
    @PostMapping("/leagues/accounts/league-entries/{page}")
    public ResponseEntity<List<SummonerLeagueAccountInfoResponseDto>> postSummonerLeagueInfoByLeagueEntry(
            @PathVariable(value = "page") @Valid @NonNull Integer page,
            @RequestBody @Valid LeagueEntryRequestDto dto) {
        return ResponseEntity.ok(summonerService.getSummonerLeagueInfo(page, dto));
    }

}