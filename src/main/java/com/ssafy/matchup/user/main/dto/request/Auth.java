package com.ssafy.matchup.user.main.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Auth {
    String username;
    String password;
}
