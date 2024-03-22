package com.ssafy.matchup.user.main.service;

import com.ssafy.matchup.user.main.dto.request.RegistUserRequestDto;
import com.ssafy.matchup.user.main.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void addUser(RegistUserRequestDto registUserRequestDto) {
        //라이엇 아이디 유효

        //라이엇 아이디 사용중인지 검사

        //저장
    }
}
