package com.bicycle.project.oauthlogin.service.impl;

import com.bicycle.project.oauthlogin.common.CommonResponse;
import com.bicycle.project.oauthlogin.config.security.CustomAuthenticationEntryPoint;
import com.bicycle.project.oauthlogin.controller.auth.dto.*;
import com.bicycle.project.oauthlogin.data.entity.User;
import com.bicycle.project.oauthlogin.data.entity.UserRefreshToken;
import com.bicycle.project.oauthlogin.exception.*;
import com.bicycle.project.oauthlogin.repository.RefreshTokenJpaRepo;
import com.bicycle.project.oauthlogin.repository.UserRepository;
import com.bicycle.project.oauthlogin.service.SignService;
import com.bicycle.project.oauthlogin.config.security.TokenProvider;

import com.nimbusds.oauth2.sdk.token.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Ref;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class SignServiceImpl /*implements SignService*/ {

    private final Logger LOGGER = LoggerFactory.getLogger(SignServiceImpl.class);


    private final PasswordEncoder passwordEncoder;
    public TokenProvider tokenProvider;

    private final  UserRepository userJpaRepo;
    private final RefreshTokenJpaRepo tokenJpaRepo;

    @Transactional
    public TokenDto signIn(SignInRequestDto userLoginRequestDto){
        User user = userJpaRepo.findByUserEmail(userLoginRequestDto.getUserEmail())
                .orElseThrow(CEmailLoginFailedException::new);

        if(!passwordEncoder.matches(userLoginRequestDto.getPassword(), user.getPassword()))
            throw new CEmailLoginFailedException();

        TokenDto tokenDto = tokenProvider.createTokenDto(user.getUserIdx(), user.getRoles());



        // RefreshToken 저장
        UserRefreshToken refreshToken = UserRefreshToken.builder()
                .key(user.getUserIdx())
                .token(tokenDto.getRefreshToken())
                .build();
        tokenJpaRepo.save(refreshToken);
        return tokenDto;
    }
    @Transactional
    public Long signup(SignUpRequestDto userSignupDto) {
        if (userJpaRepo.findByUserEmail(userSignupDto.getUserEmail()).isPresent())
            throw new CEmailSignupFailedException();
        return userJpaRepo.save(userSignupDto.toEntity(passwordEncoder)).getUserIdx();
    }

    @Transactional
    public Long socialSignup(SignUpRequestDto userSignupRequestDto) {
        if (userJpaRepo
                .findByUserEmailAndProvider(userSignupRequestDto.getUserEmail(), userSignupRequestDto.getProvider())
                .isPresent()
        ) throw new CUserExistException();
        return userJpaRepo.save(userSignupRequestDto.toEntity()).getUserIdx();
    }

    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto) throws CustomAuthenticationEntryPoint {
        // 만료된 refresh token 에러
        if (!tokenProvider.validationToken(tokenRequestDto.getRefreshToken())) {
            throw new CRefreshTokenException();
        }

        // AccessToken 에서 Username (pk) 가져오기
        String accessToken = tokenRequestDto.getAccessToken();
        Authentication authentication = tokenProvider.getAuthentication(accessToken);

        // user pk로 유저 검색 / repo 에 저장된 Refresh Token 이 없음
        User user = userJpaRepo.findById(Long.parseLong(authentication.getName()))
                .orElseThrow(CUserNotFoundException::new);
        UserRefreshToken refreshToken = tokenJpaRepo.findByKey(user.getUserIdx())
                .orElseThrow(CRefreshTokenException::new);

        // 리프레시 토큰 불일치 에러
        if (!refreshToken.getToken().equals(tokenRequestDto.getRefreshToken()))
            throw new CRefreshTokenException();

        // AccessToken, RefreshToken 토큰 재발급, 리프레쉬 토큰 저장
        TokenDto newCreatedToken = tokenProvider.createTokenDto(user.getUserIdx(), user.getRoles());
        UserRefreshToken updateRefreshToken = refreshToken.updateToken(newCreatedToken.getRefreshToken());
        tokenJpaRepo.save(updateRefreshToken);

        return newCreatedToken;
    }

//    @Autowired
//    public SignServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//        this.tokenProvider = tokenProvider;
//    }
//
//    @Override
//    public SignUpResultDto signUp(String userEmail, String password, String name, String role){
//        LOGGER.info("[getSignUpResult] 회원 가입 정보 전달");
//        User user;
//        if (role.equalsIgnoreCase("admin")) {
//            user = User.builder()
//                    .userEmail(userEmail)
//                    .username(name)
//                    .password(passwordEncoder.encode(password))
//                    .roles(Collections.singletonList("ROLE_ADMIN"))
//                    .build();
//        } else {
//            user = User.builder()
//                    .userEmail(userEmail)
//                    .username(name)
//                    .password(passwordEncoder.encode(password))
//                    .roles(Collections.singletonList("ROLE_USER"))
//                    .build();
//        }
//
//        User savedUser = userRepository.save(user);
//        SignUpResultDto signUpResultDto = new SignInResultDto();
//
//        LOGGER.info("[getSignUpResult] userEntity 값이 들어왔는지 확인 후 결과값 주입");
//        if (!savedUser.getUserEmail().isEmpty()) {
//            LOGGER.info("[getSignUpResult] 정상 처리 완료");
//            setSuccessResult(signUpResultDto);
//        } else {
//            LOGGER.info("[getSignUpResult] 실패 처리 완료");
//            setFailResult(signUpResultDto);
//        }
//        return signUpResultDto;
//    }
//
//    @Override
//    public SignInResultDto signIn(String userEmail, String password) throws RuntimeException {
//        LOGGER.info("[getSignInResult] signDataHandler 로 회원 정보 요청");
//        User user = userRepository.getById(Long.valueOf(userEmail)); //본래값을 가져오는게 아니나, uerIdx가 Long인데 제공타입이 String이므로..
//        LOGGER.info("[getSignInResult] Id : {}", userEmail);
//
//        LOGGER.info("[getSignInResult] 패스워드 비교 수행");
//        if (!passwordEncoder.matches(password, user.getPassword())) {
//            throw new RuntimeException();
//        }
//        LOGGER.info("[getSignInResult] 패스워드 일치");
//
//        LOGGER.info("[getSignInResult] SignInResultDto 객체 생성");
//        SignInResultDto signInResultDto = SignInResultDto.builder()
//                .token(tokenProvider.createTokenDto(user.getUserEmail(), user.getRoles()))
//                .build();
//
//        LOGGER.info("[getSignInResult] SignInResultDto 객체에 값 주입");
//        setSuccessResult(signInResultDto);
//
//        return signInResultDto;
//    }

    // 결과 모델에 api 요청 성공 데이터를 세팅해주는 메소드
    private void setSuccessResult(SignUpResultDto result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }

    // 결과 모델에 api 요청 실패 데이터를 세팅해주는 메소드
    private void setFailResult(SignUpResultDto result) {
        result.setSuccess(false);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMsg(CommonResponse.FAIL.getMsg());
    }

}
