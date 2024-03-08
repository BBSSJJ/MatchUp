package com.ssafy.matchup.user.test;

import com.ssafy.matchup.user.main.entity.User;
import com.ssafy.matchup.user.main.entity.type.AuthorityType;
import com.ssafy.matchup.user.main.entity.type.SnsType;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final TestUserRepository testUserRepository;
    @PostMapping("/test")
    public ResponseEntity<MessegeDTO> testRegistUser(){
        User user = User.builder()
                .snsId("test new sns id")
                .snsType(SnsType.GOOGLE)
                .role(AuthorityType.ROLE_USER)
                .build();

        testUserRepository.save(user);

        MessegeDTO messegeDTO = new MessegeDTO("테스트 User 객체 저장 성공");

        return ResponseEntity.status(200).body(messegeDTO);
    }

    @Data
    private class MessegeDTO {
        private String message;
        public MessegeDTO(String message) {
            this.message = message;
        }
    }
}
