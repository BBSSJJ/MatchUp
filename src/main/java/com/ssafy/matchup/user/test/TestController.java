package com.ssafy.matchup.user.test;

import com.ssafy.matchup.user.main.entity.User;
import com.ssafy.matchup.user.main.entity.type.AuthorityType;
import com.ssafy.matchup.user.main.entity.type.SnsType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final TestUserRepository testUserRepository;
    @PostMapping("/test")
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

    @GetMapping("/test")
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
