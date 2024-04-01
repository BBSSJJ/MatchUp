package com.ssafy.matchup_statistics.summoner.controller;

import com.ssafy.matchup_statistics.global.dto.response.MessageDto;
import com.ssafy.matchup_statistics.summoner.dto.response.SummonerLeagueInfoResponseDto;
import com.ssafy.matchup_statistics.summoner.service.SummonerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/statistics/summoners")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Summoner", description = "소환사 기본정보를 조회합니다.")
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


}