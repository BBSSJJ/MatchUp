package com.ssafy.matchup.user.main.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RsoResponse {

    private OauthResponse oauthResponse;
    private UserInfo userInfo;

    @Data
    @NoArgsConstructor
    public static class OauthResponse {
        private String idToken;
        private String refreshToken;
        private String accessToken;
        private String tokenType;
        private Long expiresIn;
    }

    @Data
    @NoArgsConstructor
    public static class UserInfo {
        private String puuid;
        private String gameName;
        private String tagLine;
    }
}
