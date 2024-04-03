package com.ssafy.matchup.user.main.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.matchup.global.dto.MessageDto;
import com.ssafy.matchup.global.util.CookieUtil;
import com.ssafy.matchup.global.util.JwtTokenUtil;
import com.ssafy.matchup.user.main.dto.UserDto;
import com.ssafy.matchup.user.main.dto.request.LoginUserRequestDto;
import com.ssafy.matchup.user.main.dto.request.RegistDumpUserRequestDto;
import com.ssafy.matchup.user.main.dto.request.RegistUserRequestDto;
import com.ssafy.matchup.user.main.dto.request.UserSnsDto;
import com.ssafy.matchup.user.main.dto.response.UserInTierResponseDto;
import com.ssafy.matchup.user.main.entity.Setting;
import com.ssafy.matchup.user.main.entity.type.SnsType;
import com.ssafy.matchup.user.main.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.util.EnumUtils;

import java.lang.runtime.ObjectMethods;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final CookieUtil cookieUtil;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/regist")
    ResponseEntity<MessageDto> userRegist(
            @RequestBody RegistUserRequestDto registUserRequestDto,
            @CookieValue(name = "user") String userCookie) throws JsonProcessingException {


        ObjectMapper objectMapper = new ObjectMapper();
        UserSnsDto userSnsDto = objectMapper.readValue(userCookie, UserSnsDto.class);

        registUserRequestDto.setSnsId(userSnsDto.getSnsId());
        registUserRequestDto.setSnsType(userSnsDto.getSnsType());
        log.info("sns id : {}",userSnsDto.getSnsId());
        log.info("sns type : {}", userSnsDto.getSnsType());

        UserDto user = userService.addUser(registUserRequestDto);

        ResponseCookie cookie = cookieUtil.createUserCookie(userSnsDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("id", String.valueOf(user.getUserId()))
                .header("role", String.valueOf(user.getRole()))
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageDto("user created"));
    }

    @PostMapping("/login")
    ResponseEntity<MessageDto> userLogin(@RequestBody LoginUserRequestDto loginUserRequestDto) throws JsonProcessingException {
        UserSnsDto user = userService.findUser(loginUserRequestDto);

        ResponseCookie cookie = cookieUtil.createUserCookie(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("id", String.valueOf(user.getUserId()))
                .header("role", String.valueOf(user.getRole()))
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageDto("login success"));
    }

    @GetMapping("/{user-id}")
    ResponseEntity<UserDto> userInfo(@PathVariable("user-id") Long userId) {
        return new ResponseEntity<>(userService.getUser(userId), HttpStatus.OK);
    }

    @GetMapping("/logout")
    ResponseEntity<Void> userLogout() {
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, String.valueOf(cookieUtil.removeUserCookie()))
                .body(null);
    }

    @Operation(summary = "설정 조회", description = "설정 조회하는 API입니다. body에 넣어서 보냅니다.")
    @GetMapping("/settings")
    ResponseEntity<Setting> settingInfo(HttpServletRequest request) {
        return new ResponseEntity<>(userService.getSetting(jwtTokenUtil.getUserId(request)), HttpStatus.OK);
    }

    @Operation(summary = "설정 변경", description = "설정 수정하는 API입니다. 마이크 사용 여부를 true, false로 받습니다")
    @PatchMapping("/settings")
    ResponseEntity<Void> settingUpdate(HttpServletRequest request, @RequestBody Setting setting) {
        userService.updateSetting(jwtTokenUtil.getUserId(request), setting);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{user-id}/tier-list")
    ResponseEntity<List<UserInTierResponseDto>> userInTierList(@PathVariable("user-id") Long userId,  @RequestParam("mic") Boolean useMike) {
        return new ResponseEntity<>(userService.getUsersInTier(userId, useMike), HttpStatus.OK);
    }

    @PostMapping("/dump/{page}")
    ResponseEntity<Void> userDumpRegist(@PathVariable("page") int page, @RequestBody RegistDumpUserRequestDto registDumpUserRequestDto) {
        userService.registDumpUser(page, registDumpUserRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
