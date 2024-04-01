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
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/statistics/summoners/records")
@Tag(name = "Summoner Records", description = "소환사의 전적정보 조회 시 필요한 API입니다.")
public class SummonerRecordController {
    private final SummonerService summonerService;

    @Operation(summary = "소환사 전적정보 조회(User PK)", description = "사용자의 id(pk)로 전적정보를 불러오는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "전적정보 응답",
                    content = @Content(schema = @Schema(implementation = SummonerRecordInfoResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "최근에 플레이한 게임이 없습니다.",
                    content = @Content(schema = @Schema(implementation = MessageDto.class)))
    })
    @GetMapping("/users/{userId}")
    public ResponseEntity<SummonerRecordInfoResponseDto> getSummonerRecordInfoByUserId(
            @PathVariable(value = "userId") Long userId) {

        return ResponseEntity.ok(summonerService.getSummonerRecordInfo(userId));
    }

    @Operation(summary = "소환사 전적정보 조회(Game Name, Tag Name)", description = "게임 이름과 태그로 전적정보를 불러오는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "전적정보 응답",
                    content = @Content(schema = @Schema(implementation = SummonerRecordInfoResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "최근에 플레이한 게임이 없습니다.",
                    content = @Content(schema = @Schema(implementation = MessageDto.class)))
    })
    @GetMapping("/riot-ids/{gameName}/tag-lines/{tagLine}")
    public ResponseEntity<SummonerRecordInfoResponseDto> getSummonerRecordInfoByGameNameAndTag(
            @PathVariable(value = "gameName") String gameName,
            @PathVariable(value = "tagLine") String tagLine) {

        return ResponseEntity.ok(summonerService.getSummonerRecordInfo(gameName, tagLine));
    }

    @Operation(summary = "소환사 전적정보 생성/갱신(User PK)", description = "사용자의 id(pk)로 전적정보를 갱신하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "전적정보 갱신 완료",
                    content = @Content(schema = @Schema(implementation = MessageDto.class))),
            @ApiResponse(responseCode = "404", description = "최근에 플레이한 게임이 없습니다.",
                    content = @Content(schema = @Schema(implementation = MessageDto.class)))
    })
    @PostMapping("/users/{userId}")
    public ResponseEntity<MessageDto> postSummonerRecordInfoByUserId(
            @PathVariable(value = "userId") Long userId) {

        summonerService.saveSummonerRecordInfo(userId);
        return ResponseEntity.ok(new MessageDto("전적정보 갱신 완료"));
    }

    @Operation(summary = "소환사 전적정보 생성/갱신(Game Name, Tag Name)", description = "게임 이름과 태그로 전적정보를 갱신하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "전적정보 갱신 완료",
                    content = @Content(schema = @Schema(implementation = MessageDto.class))),
            @ApiResponse(responseCode = "404", description = "최근에 플레이한 게임이 없습니다.",
                    content = @Content(schema = @Schema(implementation = MessageDto.class)))
    })
    @PostMapping("/riot-ids/{gameName}/tag-lines/{tagLine}")
    public ResponseEntity<MessageDto> postSummonerRecordInfoByGameNameAndTag(
            @PathVariable(value = "gameName") String gameName,
            @PathVariable(value = "tagLine") String tagLine) {

        summonerService.saveSummonerRecordInfo(gameName, tagLine);
        return ResponseEntity.ok(new MessageDto("전적정보 갱신 완료"));
    }
}
