package com.ssafy.matchup.user.main.service;

import com.ssafy.matchup.user.main.dto.request.RegistUserRequestDto;
import com.ssafy.matchup.user.main.repository.UserRepository;
import com.ssafy.matchup.user.riotaccount.api.SummonerRestApi;
import com.ssafy.matchup.user.riotaccount.api.dto.response.SummonerInfoResponseDto;
import com.ssafy.matchup.user.riotaccount.entity.RiotAccount;
import com.ssafy.matchup.user.riotaccount.respository.RiotAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    RiotAccountRepository riotAccountRepository;
    SummonerRestApi summonerRestApi;

    @Override
    public HttpStatus registUser(RegistUserRequestDto registUserRequestDto) {
        String name = registUserRequestDto.getName();

        //존재하는 이름인지 검사
        SummonerInfoResponseDto summonerInfoResponseDto = summonerRestApi.getSummonerInfoResponseDtoByName(name);
        if (summonerInfoResponseDto == null) return HttpStatus.BAD_REQUEST;

        //사용중인 이름인지 검사
        Optional<RiotAccount> riotAccountOptional =
                riotAccountRepository.findRiotAccountBySummonerProfile_Name(registUserRequestDto.getName());
        if (riotAccountOptional.isEmpty()) return HttpStatus.CONFLICT;

        //데이터 삽입


        //회원가입 성공
        return HttpStatus.CREATED;
    }
}
