package com.ssafy.matchup.user.main.service;

import com.ssafy.matchup.user.main.api.StatisticsServerApi;
import com.ssafy.matchup.user.main.api.dto.LeagueInfoDto;
import com.ssafy.matchup.user.main.api.dto.SummonerInfoDto;
import com.ssafy.matchup.user.main.api.dto.response.AccountResponseDto;
import com.ssafy.matchup.user.main.api.dto.response.SummonerLeagueInfoResponseDto;
import com.ssafy.matchup.user.main.api.flux.WebClientFactory;
import com.ssafy.matchup.user.main.dto.UserDto;
import com.ssafy.matchup.user.main.dto.request.LoginUserRequestDto;
import com.ssafy.matchup.user.main.dto.request.RegistDumpUserRequestDto;
import com.ssafy.matchup.user.main.dto.request.RegistUserRequestDto;
import com.ssafy.matchup.user.main.entity.Setting;
import com.ssafy.matchup.user.main.entity.User;
import com.ssafy.matchup.user.main.entity.type.AuthorityType;
import com.ssafy.matchup.user.main.entity.type.SnsType;
import com.ssafy.matchup.user.main.repository.UserRepository;
import com.ssafy.matchup.user.main.service.sub.InitUserService;
import com.ssafy.matchup.user.riotaccount.entity.RiotAccount;
import com.ssafy.matchup.user.riotaccount.entity.SummonerProfile;
import com.ssafy.matchup.user.riotaccount.respository.RiotAccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Value("${ddragon.version}")
    String ddragonVersion;

    private final UserRepository userRepository;
    private final RiotAccountRepository riotAccountRepository;
    private final StatisticsServerApi statisticsServerApi;
    private final InitUserService initUserService;
    private final WebClientFactory webClientFactory;

    @Transactional
    @Override
    public UserDto addUser(RegistUserRequestDto registUserRequestDto) {
        String summonerName = registUserRequestDto.getRiotId();
        String[] parts = summonerName.split("#");
        String name = parts[0].trim();
        String tag = parts[1].trim();

        //라이엇 아이디 유효
        SummonerLeagueInfoResponseDto summonerLeagueInfoResponseDto =
                statisticsServerApi.getRiotAccountInfo(name, tag);
        if (summonerLeagueInfoResponseDto == null) throw new UsernameNotFoundException(summonerName);

        //라이엇 아이디 사용중인지 검사
        Optional<RiotAccount> riotAccountOptional = riotAccountRepository.findRiotAccountBySummonerProfile_NameAndSummonerProfile_Tag(name, tag);
        if (riotAccountOptional.isPresent()) throw new DuplicateKeyException(summonerName);

        //TODO : Statistics Server에 전적 분석 요청
        webClientFactory.sendSummonerName(name, tag).subscribe(m -> log.info(""));

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

    @Transactional(readOnly = true)
    @Override
    public UserDto findUser(LoginUserRequestDto loginUserRequestDto) {
        User user = userRepository.findUserBySnsTypeAndSnsId(loginUserRequestDto.getSnsType(),
                loginUserRequestDto.getSnsId()).orElseThrow(EntityNotFoundException::new);

        // TODO : 유저 업데이트, 닉네임 변경사항 알 수 있도록 response dto 변경요청
//        SummonerLeagueInfoResponseDto summonerLeagueInfoResponseDto =
//                statisticsServerApi.getRiotAccountInfo(user.getRiotAccount().getSummonerProfile().getName(),
//                        user.getRiotAccount().getSummonerProfile().getTag());
//        if (summonerLeagueInfoResponseDto == null) throw new EntityNotFoundException();


        return new UserDto(user);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDto getUser(Long userId) {
        return new UserDto(userRepository.getReferenceById(userId));
    }

    @Override
    public Setting getSetting(Long userId) {
        User user = userRepository.findSettingById(userId).orElseThrow(EntityNotFoundException::new);
        return user.getSetting();
    }

    @Transactional
    @Override
    public void updateSetting(Long userId, Setting setting) {
        User user = userRepository.findSettingById(userId).orElseThrow(EntityNotFoundException::new);
        user.updateSetting(setting);
    }

    @Transactional
    @Override
    public void registDumpUser(int page, RegistDumpUserRequestDto registDumpUserRequestDto) {
        List<SummonerLeagueInfoResponseDto> dtoList = webClientFactory.getDumpRiotAccount(page, registDumpUserRequestDto);

        List<String> idList = new ArrayList<>();

        for (SummonerLeagueInfoResponseDto dto : dtoList) {

            LeagueInfoDto leagueInfoDto = dto.getLeagueInfoDto();
            SummonerInfoDto summonerInfoDto = dto.getSummonerInfoDto();
            AccountResponseDto accountResponseDto = dto.getAccountResponseDto();

            User user = User.builder()
                    .snsType(SnsType.NAVER)
                    .snsId(RandomStringUtils.random(30, true, true))
                    .role(AuthorityType.ROLE_USER)
                    .build();
            userRepository.save(user);

            SummonerProfile summonerProfile = SummonerProfile.builder()
                    .name(accountResponseDto.getGameName())
                    .tag(accountResponseDto.getTagLine())
                    .iconUrl("https://ddragon.leagueoflegends.com/cdn/" + ddragonVersion
                            + "/img/profileicon/" + summonerInfoDto.getProfileIconId() + ".png")
                    .level(summonerInfoDto.getSummonerLevel()).build();

            RiotAccount riotAccount = RiotAccount.builder()
                    .id(summonerInfoDto.getId())
                    .revisionDate(summonerInfoDto.getRevisionDate())
                    .summonerProfile(summonerProfile)
                    .tier(leagueInfoDto.getTier())
                    .leagueRank(leagueInfoDto.getRank())
                    .leaguePoint(leagueInfoDto.getLeaguePoints())
                    .build();

            idList.add(summonerInfoDto.getId());

            riotAccountRepository.save(riotAccount);

            User newUser = userRepository.getReferenceById(user.getId());
            RiotAccount newRiotAccount = riotAccountRepository.getReferenceById(riotAccount.getId());

            newRiotAccount.updateUser(newUser);
            newUser.updateRiotAccount(newRiotAccount);


//            webClientFactory.sendSummonerName(summonerInfoDto.getName(), summonerProfile.getTag())
//                    .subscribe(m -> log.info("message : {} ", m));
            webClientFactory.sendSummonerName(summonerInfoDto.getName(), summonerProfile.getTag())
                    .block();
        }

        log.info("page {} / tier {} {} : {}", page, registDumpUserRequestDto.getTier(), registDumpUserRequestDto.getDivision(), idList.toString());
    }
}
