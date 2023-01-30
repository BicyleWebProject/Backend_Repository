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
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public TokenDto reissue(TokenRequestDto tokenRequestDto) throws CustomAuthenticationEntryPoint {
        if(!tokenProvider.validationToken(tokenRequestDto.getRefreshToken())){
            throw new CRefreshTokenException();
        }

        String accessToken = tokenRequestDto.getAccessToken();
        Authentication authentication = tokenProvider.getAuthentication(accessToken);

        User user = userRepository.findByUserIdx(Long.parseLong(authentication.getName()))
                .orElseThrow(CUserNotFoundException::new);
        UserRefreshToken refreshToken = tokenJpaRepo.findByUserKey(user.getUserIdx())
                .orElseThrow(CRefreshTokenException::new);

        if(!refreshToken.getToken().equals(tokenRequestDto.getRefreshToken()))
            throw new CRefreshTokenException();

        TokenDto newCreatedToken = tokenProvider.createTokenDto(user.getUserIdx(), user.getRoles());
        UserRefreshToken updateRefreshToken = refreshToken.updateToken(newCreatedToken.getRefreshToken());
        tokenJpaRepo.save(updateRefreshToken);

        return newCreatedToken;
    }
}
