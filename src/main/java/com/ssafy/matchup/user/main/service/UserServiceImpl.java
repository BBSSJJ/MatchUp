package com.ssafy.matchup.user.main.service;

import com.ssafy.matchup.user.main.api.dto.response.AccountResponseDto;
import com.ssafy.matchup.user.main.api.dto.response.LeagueInfoDto;
import com.ssafy.matchup.user.main.api.dto.response.SummonerInfoDto;
import com.ssafy.matchup.user.main.api.dto.response.SummonerLeagueAccountInfoResponseDto;
import com.ssafy.matchup.user.main.api.flux.WebClientFactory;
import com.ssafy.matchup.user.main.dto.UserDto;
import com.ssafy.matchup.user.main.dto.request.LoginUserRequestDto;
import com.ssafy.matchup.user.main.dto.request.RegistDumpUserRequestDto;
import com.ssafy.matchup.user.main.dto.request.RegistUserRequestDto;
import com.ssafy.matchup.user.main.dto.request.UserSnsDto;
import com.ssafy.matchup.user.main.dto.response.OauthResponse;
import com.ssafy.matchup.user.main.dto.response.UserInTierResponseDto;
import com.ssafy.matchup.user.main.entity.Setting;
import com.ssafy.matchup.user.main.entity.User;
import com.ssafy.matchup.user.main.entity.type.AuthorityType;
import com.ssafy.matchup.user.main.entity.type.SnsType;
import com.ssafy.matchup.user.main.repository.UserRepository;
import com.ssafy.matchup.user.main.service.sub.UserInitService;
import com.ssafy.matchup.user.main.service.sub.UserTierCheckService;
import com.ssafy.matchup.user.riotaccount.entity.RiotAccount;
import com.ssafy.matchup.user.riotaccount.entity.SummonerProfile;
import com.ssafy.matchup.user.riotaccount.respository.RiotAccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.tuple.Pair;
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
    private final UserInitService userInitService;
    private final UserTierCheckService userTierCheckService;
    private final WebClientFactory webClientFactory;

    @Transactional
    @Override
    public UserDto addUser(RegistUserRequestDto registUserRequestDto) {
        String riotCode = registUserRequestDto.getRiotCode();
        log.info("riot code : {}", riotCode);
        OauthResponse oauthResponse = webClientFactory.getRiotAccountByRiotCode(riotCode).block();
        if (oauthResponse == null) throw new IllegalArgumentException("invalid riot code");

        log.info("access token : {}", oauthResponse.getAccessToken());
        AccountResponseDto accountResponseDto = webClientFactory.getAccountResponseDtoByToken(oauthResponse.getTokenType(), oauthResponse.getAccessToken()).block();
        if (accountResponseDto == null) throw new IllegalArgumentException("invalid account info");

        String name = accountResponseDto.getGameName();
        String tag = accountResponseDto.getTagLine();
        SummonerLeagueAccountInfoResponseDto summonerLeagueAccountInfoResponseDto = webClientFactory.postByNameAndTag(name, tag).block();

        // 라이엇 아이디 유효
        if (summonerLeagueAccountInfoResponseDto == null) {
            throw new UsernameNotFoundException("user name doesn't exist");
        }

        // 라이엇 아이디 사용중인지 검사
        Optional<RiotAccount> riotAccountOptional = riotAccountRepository
                .findRiotAccountBySummonerProfile_NameAndSummonerProfile_Tag(name, tag);
        if (riotAccountOptional.isPresent()) throw new DuplicateKeyException("already registed user!");

        //저장
        //TODO : 최적화 필요
        RiotAccount riotAccount = userInitService.initRiotAccount(summonerLeagueAccountInfoResponseDto);
        User user = userInitService.initUser(registUserRequestDto);

        User newUser = userRepository.getReferenceById(user.getId());
        RiotAccount newRiotAccount = riotAccountRepository.getReferenceById(riotAccount.getId());

        newRiotAccount.updateUser(newUser);
        newUser.updateRiotAccount(newRiotAccount);

        return new UserDto(newUser);
    }

    @Transactional
    @Override
    public UserSnsDto findUser(LoginUserRequestDto loginUserRequestDto) {
        log.info("in findUser" );
        User user = userRepository.findUserBySnsTypeAndSnsId(loginUserRequestDto.getSnsType(),
                loginUserRequestDto.getSnsId()).orElseThrow(EntityNotFoundException::new);
        log.info("user found" );
        SummonerLeagueAccountInfoResponseDto summonerLeagueAccountInfoResponseDto =
                webClientFactory.postById(user.getRiotAccount().getId()).block();
        if (summonerLeagueAccountInfoResponseDto == null) throw new UsernameNotFoundException("no user");
        log.info("before update user" );

        userInitService.updateInfo(user, summonerLeagueAccountInfoResponseDto);
        log.info("after update info" );

        return new UserSnsDto(new UserDto(user), user.getSnsId(), user.getSnsType());
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

    @Override
    public List<UserInTierResponseDto> getUsersInTier(Long userId, Boolean useMike) {
        User user = userRepository.findUserById(userId).orElseThrow(EntityNotFoundException::new);

//        Boolean useMike = user.getSetting().getUseMike();
        String tier = user.getRiotAccount().getTier();
        String leagueRank = user.getRiotAccount().getLeagueRank();

        List<Pair<String, String>> tierLeagueRankList = userTierCheckService.getTierList(tier, leagueRank);

        if ("no league data".equals(tier) || "MASTER".equals(tier) || "GRANDMASTER".equals(tier) || "CHALLENGER".equals(tier))
            return null;

        return userRepository.findByTierAndLeagueRankAndUseMike(tierLeagueRankList, useMike).stream()
                .filter(u -> !u.getRiotAccount().getId().equals(user.getRiotAccount().getId()))
                .map(UserInTierResponseDto::new).toList();
    }

    @Transactional
    @Override
    public void registDumpUser(int page, RegistDumpUserRequestDto registDumpUserRequestDto) {
        List<SummonerLeagueAccountInfoResponseDto> dtoList = webClientFactory.getDumpRiotAccount(page, registDumpUserRequestDto);

        List<String> idList = new ArrayList<>();

        for (SummonerLeagueAccountInfoResponseDto dto : dtoList) {

            LeagueInfoDto leagueInfoDto = dto.getLeagueInfoDto();
            SummonerInfoDto summonerInfoDto = dto.getSummonerInfoDto();
            AccountResponseDto accountResponseDto = dto.getAccountResponseDto();

            Setting setting = new Setting(false);

            User user = User.builder()
                    .snsType(SnsType.NAVER)
                    .snsId(RandomStringUtils.random(30, true, true))
                    .role(AuthorityType.ROLE_USER)
                    .setting(setting)
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
//            webClientFactory.postById(summonerInfoDto.getName(), summonerProfile.getTag())
//                    .block();
        }

        log.info("page {} / tier {} {} : {}", page, registDumpUserRequestDto.getTier(), registDumpUserRequestDto.getDivision(), idList.toString());
    }
}
