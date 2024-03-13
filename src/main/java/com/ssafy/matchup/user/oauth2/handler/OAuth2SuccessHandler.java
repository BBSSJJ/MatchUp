package com.ssafy.matchup.user.oauth2.handler;

import com.ssafy.matchup.user.main.entity.User;
import com.ssafy.matchup.user.main.entity.type.SnsType;
import com.ssafy.matchup.user.main.repository.UserRepository;
import com.ssafy.matchup.user.oauth2.CustomOAuth2User;
import com.ssafy.matchup.user.oauth2.api.JwtTokenApi;
import com.ssafy.matchup.user.oauth2.dto.JwtTokenRequestDto;
import com.ssafy.matchup.user.oauth2.dto.JwtTokenResponseDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@AllArgsConstructor
@Slf4j
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    UserRepository userRepository;
    JwtTokenApi jwtTokenApi;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        SnsType snsType = oAuth2User.getSnsType();
        String snsId = oAuth2User.getSnsId();

        Optional<User> optionalUser = userRepository.findUserBySnsTypeAndSnsId(snsType, snsId);

        // TODO : snsType, snsId 넣어서 회원가입 페이지로 redirect 시키기
        if (optionalUser.isEmpty()) {
            response.sendRedirect("/signup");
        }
        // TODO : 접속 상태 확인하는 redis에 유저 저장시키기
        // TODO : JWT Token 달아서 보내기
        else {
            User user = optionalUser.get();
            JwtTokenResponseDto jwtToken = jwtTokenApi.issueJwtToken(new JwtTokenRequestDto(user.getId(), user.getRole()));
            response.sendRedirect("/index");
        }
    }
}
