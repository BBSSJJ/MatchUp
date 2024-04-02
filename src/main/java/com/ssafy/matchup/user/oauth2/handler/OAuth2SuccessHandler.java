package com.ssafy.matchup.user.oauth2.handler;

import com.ssafy.matchup.global.util.CookieUtil;
import com.ssafy.matchup.user.main.dto.UserDto;
import com.ssafy.matchup.user.main.dto.request.UserSnsDto;
import com.ssafy.matchup.user.main.entity.User;
import com.ssafy.matchup.user.main.entity.type.SnsType;
import com.ssafy.matchup.user.main.repository.UserRepository;
import com.ssafy.matchup.user.oauth2.CustomOAuth2User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
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

    @Value("${url.domain}")
    String domainUrl;

    @Value("${RIOT_CLIENT_ID}")
    String clientId;

    @Value("${RIOT_REDIRECT_URI}")
    String riotRedirectUri;

    private final UserRepository userRepository;
    private final CookieUtil cookieUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("in AuthenticationSuccess : {}", authentication.toString());

        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        SnsType snsType = oAuth2User.getSnsType();
        String snsId = oAuth2User.getSnsId();

        log.info("get user data success : {} / {}", snsType, snsId);
        log.info(oAuth2User.toString());

        Optional<User> optionalUser = userRepository.findUserBySnsTypeAndSnsId(snsType, snsId);

        if (optionalUser.isEmpty()) {
            ResponseCookie userCookie = cookieUtil.createUserCookie(new UserSnsDto(snsId, snsType));
            response.addHeader(HttpHeaders.SET_COOKIE, userCookie.toString());
            response.sendRedirect("http://" + domainUrl + "/login/" + snsType + "/" + snsId + "/" + true);
        }
        // TODO : 접속 상태 확인하는 redis에 유저 저장시키기
        else {
            User user = optionalUser.get();

//            response.setHeader("id", user.getId().toString());
//            response.setHeader("role", user.getRole().toString());

//            response.setCharacterEncoding("UTF-8");
//            response.setContentType("application/json");
//            ResponseEntity<UserDto> entity = ResponseEntity.status(HttpStatus.OK).body(new UserDto(user));
//            response.getWriter().write(String.valueOf(entity.getBody()));
//            response.setStatus(HttpStatus.OK.value());
//            log.info("response header : {} {}", response.getHeaders("id"), response.getHeaders("role"));
            response.sendRedirect("http://" + domainUrl + "/login/" + snsType + "/" + snsId + "/" + false);
        }
    }
}
