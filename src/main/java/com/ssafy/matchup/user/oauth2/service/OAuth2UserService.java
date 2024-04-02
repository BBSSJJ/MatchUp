package com.ssafy.matchup.user.oauth2.service;

import com.ssafy.matchup.user.main.entity.type.SnsType;
import com.ssafy.matchup.user.main.repository.UserRepository;
import com.ssafy.matchup.user.oauth2.CustomOAuth2User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class OAuth2UserService extends DefaultOAuth2UserService {

    UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        log.info("in loadUser : {}", request.toString());

        OAuth2User oAuth2User = super.loadUser(request);

        // oAuth2User을 sns_type, sns_id로 변환
        SnsType snsType = SnsType.valueOf(request.getClientRegistration().getRegistrationId().toUpperCase());
        String snsId = null;

        if ("GOOGLE".equals(snsType.name())) {
            snsId = oAuth2User.getAttributes().get("sub").toString();
        } else if ("KAKAO".equals(snsType.name())) {
            snsId = oAuth2User.getAttributes().get("id").toString();
        } else if ("NAVER".equals(snsType.name())) {
            Map<String, String> responeMap = (Map<String, String>) oAuth2User.getAttributes().get("response");
            snsId = responeMap.get("id");
        }

        log.info("SNS Type / ID : {} / {}", snsType, snsId);

        return new CustomOAuth2User(snsType, snsId);
    }
}
