package com.ssafy.matchup.user.oauth2.handler;

import com.ssafy.matchup.user.main.dto.UserDto;
import com.ssafy.matchup.user.main.entity.User;
import com.ssafy.matchup.user.main.entity.type.SnsType;
import com.ssafy.matchup.user.main.repository.UserRepository;
import com.ssafy.matchup.user.oauth2.CustomOAuth2User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${ip.server.web}")
    String webServer;

    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        SnsType snsType = oAuth2User.getSnsType();
        String snsId = oAuth2User.getSnsId();

        log.info("get user data success : {} / {}", snsType, snsId);
        log.info(oAuth2User.toString());

        Optional<User> optionalUser = userRepository.findUserBySnsTypeAndSnsId(snsType, snsId);

        // TODO : 유저 정보랑 함께 보내주기
        if (optionalUser.isEmpty()) {
//            response.setHeader("Sns-Type", String.valueOf(snsType));
//            response.setHeader("Sns-Id", snsId);
            response.sendRedirect("http://" + webServer + ":3000/login/riot-login/" + snsType + "/" + snsId);
        }
        // TODO : 접속 상태 확인하는 redis에 유저 저장시키기
        else {
            User user = optionalUser.get();

            response.setHeader("id", user.getId().toString());
            response.setHeader("role", user.getRole().toString());

            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json"); // Content-Type 설정
            ResponseEntity<UserDto> entity = ResponseEntity.status(HttpStatus.OK).body(new UserDto(user));
            response.getWriter().write(String.valueOf(entity.getBody()));
//            response.setStatus(HttpStatus.OK.value());
        }
    }
}
