package com.ssafy.matchup.user.main.service;

import com.ssafy.matchup.user.main.api.StatisticsServerApi;
import com.ssafy.matchup.user.main.api.dto.response.SummonerLeagueInfoResponseDto;
import com.ssafy.matchup.user.main.dto.UserDto;
import com.ssafy.matchup.user.main.dto.request.RegistUserRequestDto;
import com.ssafy.matchup.user.main.entity.User;
import com.ssafy.matchup.user.main.repository.UserRepository;
import com.ssafy.matchup.user.main.service.sub.InitUserService;
import com.ssafy.matchup.user.riotaccount.entity.RiotAccount;
import com.ssafy.matchup.user.riotaccount.respository.RiotAccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RiotAccountRepository riotAccountRepository;
    private final StatisticsServerApi statisticsServerApi;
    private final InitUserService initUserService;

    @Override
    public UserDto addUser(RegistUserRequestDto registUserRequestDto) {
        String summonerName = registUserRequestDto.getSummonerName();
        String[] parts = summonerName.split("#");
        String name = parts[0].trim();
        String tag = parts[1].trim();

        //라이엇 아이디 유효
        SummonerLeagueInfoResponseDto summonerLeagueInfoResponseDto =
                statisticsServerApi.getRiotAccountInfo(name, tag);
        if (summonerLeagueInfoResponseDto == null) throw new UsernameNotFoundException(summonerName);

        //라이엇 아이디 사용중인지 검사
        Optional<RiotAccount> riotAccountOptional =
                riotAccountRepository.findRiotAccountBySummonerProfile_NameAndSummonerProfile_Tag(name, tag);
        if (riotAccountOptional.isPresent()) throw new DuplicateKeyException(summonerName);

        //저장
        //TODO : 최적화 필요
        RiotAccount riotAccount = initUserService.initRiotAccount(registUserRequestDto, summonerLeagueInfoResponseDto);
        User user = initUserService.initUser(registUserRequestDto);

        User newUser = userRepository.getReferenceById(user.getId());
        RiotAccount newRiotAccount = riotAccountRepository.getReferenceById(riotAccount.getId());

        newRiotAccount.updateUser(newUser);
        newUser.updateRiotAccount(newRiotAccount);


        return new UserDto(newUser);
    }
}
