package com.ssafy.matchup_statistics.summoner.controller;

import com.ssafy.matchup_statistics.global.dto.response.MessageDto;
import com.ssafy.matchup_statistics.global.dto.response.SummonerInfoResponseDto;
import com.ssafy.matchup_statistics.summoner.dto.response.SummonerLeagueAccountInfoResponseDto;
import com.ssafy.matchup_statistics.summoner.service.SummonerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistics/users")
@RequiredArgsConstructor
@Tag(name = "Summoner Users", description = "회원가입 및 로그인 시 필요한 API입니다.")
public class SummonerUserController {

    private final SummonerService summonerService;

    @Operation(summary = "소환사 회원가입 시 지표 저장 후 라이엇 정보 반환(Game Name, Tag Name)", description = "회원가입 시 20게임을 불러와 지표를 생성 및 저장 후, 해당 라이엇 계정 정보를 반환합니다.") // 해당 API가 어떤 역할을 하는지 설명
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "모든 정보 생성 및 저장완료, 라이엇 정보 반환", // 응답코드 200일때 응답 설명
                    content = @Content(schema = @Schema(implementation = SummonerLeagueAccountInfoResponseDto.class))), // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
            @ApiResponse(responseCode = "404", description = "라이엇 API에 요청 정보가 없습니다.", // 응답코드 400일때 응답 설명
                    content = @Content(schema = @Schema(implementation = MessageDto.class))) // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
    })
    @PostMapping("/riot-ids/{gameName}/tag-lines/{tagLine}/regist")
    public ResponseEntity<SummonerLeagueAccountInfoResponseDto> registSummonerInfo(
            @PathVariable(value = "gameName") String gameName,
            @PathVariable(value = "tagLine") String tagLine) {
        return ResponseEntity.ok(summonerService.registSummoner(gameName, tagLine));
    }

    @Operation(summary = "소환사 로그인 시 지표 갱신 후 라이엇 정보 반환(userId)", description = "로그인 시 DB에 없는 매치정보가 있는지 확인하여 갱신 후, 해당 라이엇 계정 정보를 반환합니다.") // 해당 API가 어떤 역할을 하는지 설명
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "모든 정보 갱신완료, 라이엇 정보 반환", // 응답코드 200일때 응답 설명
                    content = @Content(schema = @Schema(implementation = SummonerLeagueAccountInfoResponseDto.class))), // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
            @ApiResponse(responseCode = "404", description = "라이엇 API에 요청 정보가 없습니다.", // 응답코드 400일때 응답 설명
                    content = @Content(schema = @Schema(implementation = MessageDto.class))) // 해당 응답코드에서 어떤 클래스를 응답하는지 작성
    })
    @PostMapping("/{userId}/login")
    public ResponseEntity<SummonerLeagueAccountInfoResponseDto> loginSummonerInfo(
            @PathVariable(value = "userId") Long userId) {
        return ResponseEntity.ok(summonerService.loginSummoner(userId));
    }
}
