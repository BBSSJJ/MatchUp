package com.ssafy.matchup.user.test;

import com.ssafy.matchup.user.main.entity.User;
import com.ssafy.matchup.user.main.entity.type.AuthorityType;
import com.ssafy.matchup.user.main.entity.type.SnsType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "테스트", description = "Swagger Test를 위한 Test API 입니다.")
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final TestUserRepository testUserRepository;

    @Operation(summary = "사용자 등록", description = "사용자를 등록하는 API입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 정보 등록 성공",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = MessegeDTO.class)))
    })
    @PostMapping("/users")
    public ResponseEntity<MessegeDTO> testRegistUser(@RequestParam(name = "snsId") String snsId){
        User user = User.builder()
                .snsId(snsId)
                .snsType(SnsType.GOOGLE)
                .role(AuthorityType.ROLE_USER)
                .build();

        testUserRepository.save(user);

        MessegeDTO messegeDTO = new MessegeDTO("테스트 User 객체 저장 성공");

        return ResponseEntity.status(200).body(messegeDTO);
    }

    @Operation(summary = "사용자 조회", description = "전체 사용자를 조회하는 API입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 정보 조회 성공",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = MessegeDTO.class)))
    })
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> testGetUserInfo() {
        List<User> all = testUserRepository.findAll();
        List<UserDTO> list = all.stream().map(user -> UserDTO.builder()
                .snsId(user.getSnsId())
                .snsType(user.getSnsType())
                .role(user.getRole())
                .build()).toList();

        return ResponseEntity.status(200).body(list);
    }

    @Data
    private class MessegeDTO {
        private String message;
        public MessegeDTO(String message) {
            this.message = message;
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    private static class UserDTO {
        private String snsId;
        private SnsType snsType;
        private AuthorityType role;
    }
}
