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

import javax.servlet.http.HttpServletRequest;
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



        // RefreshToken ??????
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

        LOGGER.info(String.valueOf(userSignupDto.getRoles()) + "abc");
        return userJpaRepo.save(userSignupDto.toEntity(passwordEncoder)).getUserIdx();
    }

//    @Transactional
//    public Long socialSignup(SignUpRequestDto userSignupRequestDto) {
//        if (userJpaRepo
//                .findByUserEmailAndProvider(userSignupRequestDto.getUserEmail(), userSignupRequestDto.getProvider())
//                .isPresent()
//        ) throw new CUserExistException();
//        return userJpaRepo.save(userSignupRequestDto.toEntity()).getUserIdx();
//    }

    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto, HttpServletRequest request) throws CustomAuthenticationEntryPoint {
        // ????????? refresh token ??????
        if (!tokenProvider.validationToken(tokenRequestDto.getRefreshToken())) {
            throw new CRefreshTokenException();
        }

        // AccessToken ?????? Username (pk) ????????????
        String accessToken = tokenRequestDto.getAccessToken();
        Authentication authentication = tokenProvider.getAuthentication(accessToken);

        // user pk??? ?????? ?????? / repo ??? ????????? Refresh Token ??? ??????
        User user = userJpaRepo.findByUserIdx(Long.valueOf(tokenProvider.getUserPk(tokenProvider.resolveToken(request))))
                .orElseThrow(CUserNotFoundException::new);
        UserRefreshToken refreshToken = (UserRefreshToken) tokenJpaRepo.findTop1ByUserKeyOrderByUpdatedAtDesc(user.getUserIdx())
                .orElseThrow(CRefreshTokenException::new);

        // ???????????? ?????? ????????? ??????
        if (!refreshToken.getToken().equals(tokenRequestDto.getRefreshToken()))
            throw new CRefreshTokenException();

        // AccessToken, RefreshToken ?????? ?????????, ???????????? ?????? ??????
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
//        LOGGER.info("[getSignUpResult] ?????? ?????? ?????? ??????");
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
//        LOGGER.info("[getSignUpResult] userEntity ?????? ??????????????? ?????? ??? ????????? ??????");
//        if (!savedUser.getUserEmail().isEmpty()) {
//            LOGGER.info("[getSignUpResult] ?????? ?????? ??????");
//            setSuccessResult(signUpResultDto);
//        } else {
//            LOGGER.info("[getSignUpResult] ?????? ?????? ??????");
//            setFailResult(signUpResultDto);
//        }
//        return signUpResultDto;
//    }
//
//    @Override
//    public SignInResultDto signIn(String userEmail, String password) throws RuntimeException {
//        LOGGER.info("[getSignInResult] signDataHandler ??? ?????? ?????? ??????");
//        User user = userRepository.getById(Long.valueOf(userEmail)); //???????????? ??????????????? ?????????, uerIdx??? Long?????? ??????????????? String?????????..
//        LOGGER.info("[getSignInResult] Id : {}", userEmail);
//
//        LOGGER.info("[getSignInResult] ???????????? ?????? ??????");
//        if (!passwordEncoder.matches(password, user.getPassword())) {
//            throw new RuntimeException();
//        }
//        LOGGER.info("[getSignInResult] ???????????? ??????");
//
//        LOGGER.info("[getSignInResult] SignInResultDto ?????? ??????");
//        SignInResultDto signInResultDto = SignInResultDto.builder()
//                .token(tokenProvider.createTokenDto(user.getUserEmail(), user.getRoles()))
//                .build();
//
//        LOGGER.info("[getSignInResult] SignInResultDto ????????? ??? ??????");
//        setSuccessResult(signInResultDto);
//
//        return signInResultDto;
//    }

    // ?????? ????????? api ?????? ?????? ???????????? ??????????????? ?????????
    private void setSuccessResult(SignUpResultDto result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }

    // ?????? ????????? api ?????? ?????? ???????????? ??????????????? ?????????
    private void setFailResult(SignUpResultDto result) {
        result.setSuccess(false);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMsg(CommonResponse.FAIL.getMsg());
    }

}
