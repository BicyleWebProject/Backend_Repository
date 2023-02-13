package com.bicycle.project.oauthlogin.service;

import com.bicycle.project.oauthlogin.Type.RoleType;
import com.bicycle.project.oauthlogin.common.Role;
import com.bicycle.project.oauthlogin.config.security.CustomAuthenticationEntryPoint;
import com.bicycle.project.oauthlogin.config.security.TokenProvider;
import com.bicycle.project.oauthlogin.controller.auth.dto.*;
import com.bicycle.project.oauthlogin.data.entity.User;
import com.bicycle.project.oauthlogin.data.entity.UserRefreshToken;
import com.bicycle.project.oauthlogin.exception.CEmailLoginFailedException;
import com.bicycle.project.oauthlogin.exception.CRefreshTokenException;
import com.bicycle.project.oauthlogin.exception.CUserNotFoundException;
import com.bicycle.project.oauthlogin.repository.RefreshTokenJpaRepo;
import com.bicycle.project.oauthlogin.repository.UserRepository;
import com.nimbusds.oauth2.sdk.token.RefreshToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignService {
    //securityService와 같음
//    SignUpResultDto signUp(String userEmail, String password, String name, String role);
//
//    SignInResultDto signIn(String userEmail, String password) throws RuntimeException;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenJpaRepo tokenJpaRepo;

    Logger logger = LoggerFactory.getLogger(SignService.class);

    @Transactional
    public TokenDto signIn(LoginDto signInRequestDto){
        //public TokenDto signIn(SignInRequestDto signInRequestDto
        User user = userRepository.findByUserEmail(signInRequestDto.getUserEmail())
                .orElseThrow(CEmailLoginFailedException::new);

        log.info(String.format("dto : %s , user : %s", signInRequestDto.getPassword(), user.getPassword()));
        if(!passwordEncoder.matches(signInRequestDto.getPassword(), user.getPassword()))
            throw new CEmailLoginFailedException();

        TokenDto tokenDto = tokenProvider.createTokenDto(user.getUserIdx(), user.getRoles());

        UserRefreshToken refreshToken = UserRefreshToken.builder()
                .key(user.getUserIdx())
                .token(tokenDto.getRefreshToken())
                .build();
        tokenJpaRepo.save(refreshToken);
        return tokenDto;
    }

    @Transactional
    public Long signUp(SignUpRequestDto signUpRequestDto){
        if(userRepository.findByUserEmail(signUpRequestDto.getUserEmail()).isPresent())
            throw new CEmailLoginFailedException();
        signUpRequestDto.setRoles(Role.ROLE_USER);
        return userRepository.save(signUpRequestDto.toEntity(passwordEncoder)).getUserIdx();
    }

    //만료된 토큰 재발급
    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto, HttpServletRequest request) throws CustomAuthenticationEntryPoint {
        //refresh token 만료시 에러
        if(!tokenProvider.validationToken(tokenRequestDto.getRefreshToken())){
            throw new CRefreshTokenException();
        }

        String accessToken = tokenRequestDto.getAccessToken();
        logger.info("res1");
        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        logger.info("res2");
        //userIdx -> PK값을 뽑아 와야 되는데, JPA를 사용하면 username을 가져옴 -> 변환해야됨
        //findUserIdxByUsername() 메서드 사용..?
//        User user = userRepository.findByUserIdx(userRepository.findUserIdxByUsername(authentication.getName()))
//                .orElseThrow(CUserNotFoundException::new);
        User user = userRepository.findByUserIdx(Long.valueOf(tokenProvider.getUserPk(tokenProvider.resolveToken(request))))
                .orElseThrow(CUserNotFoundException::new);
        logger.info("res3");
        UserRefreshToken refreshToken = tokenJpaRepo.findTop1ByUserKeyOrderByUpdatedAtDesc(user.getUserIdx())
                .orElseThrow(CRefreshTokenException::new);
        logger.info("res4");
        if(!refreshToken.getToken().equals(tokenRequestDto.getRefreshToken()))
            throw new CRefreshTokenException();
        logger.info("res5");
        TokenDto newToken = tokenProvider.createTokenDto(user.getUserIdx(), user.getRoles());
        logger.info("res6");
        UserRefreshToken updateRefreshToken = refreshToken.updateToken(newToken.getRefreshToken());
        logger.info("res7");
        tokenJpaRepo.save(updateRefreshToken);
        logger.info("res8");

        return newToken;
    }
}
