package com.bicycle.project.oauthlogin.controller;

import com.bicycle.project.oauthlogin.common.SingleResult;
import com.bicycle.project.oauthlogin.config.security.CustomAuthenticationEntryPoint;
import com.bicycle.project.oauthlogin.config.security.TokenProvider;
import com.bicycle.project.oauthlogin.controller.auth.dto.*;
import com.bicycle.project.oauthlogin.repository.UserRepository;
import com.bicycle.project.oauthlogin.service.ResponseService;
import com.bicycle.project.oauthlogin.service.SignService;
import com.bicycle.project.oauthlogin.service.impl.SignServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/sign-api")
public class SignController {

    private final Logger LOGGER = LoggerFactory.getLogger(SignController.class);

    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final SignService signService;
    private final ResponseService responseService;

    @ApiOperation(value = "signIn", notes = "email login")
    @PostMapping("/sign-in")
    public SingleResult<TokenDto> signIn(
            @ApiParam(value="login-dto", required = true)
            @RequestBody LoginDto signInRequestDto, HttpServletResponse response){

        LOGGER.info("로그인 과정입니다.");
        //한번 출력
        LOGGER.info(String.valueOf(signInRequestDto.getUserEmail()) + 1);
        TokenDto tokenDto = signService.signIn(signInRequestDto);
        LOGGER.info(String.valueOf(signInRequestDto.getUserEmail()) + 2);
        response.addHeader("X-AUTH-TOKEN", tokenDto.getAccessToken());
        return responseService.getSingleResult(tokenDto);
    }

    @ApiOperation(value="signup", notes = "회원가입")
    @PostMapping("/sign-up")
    public SingleResult<Long> signup(
            @ApiParam(value="signup-dto", required = true)
            @RequestBody SignUpRequestDto signUpRequestDto){
        Long signUpId = signService.signUp(signUpRequestDto);
        return responseService.getSingleResult(signUpId);
    }

    @PostMapping("/reissue")
    public SingleResult<TokenDto> reissue(
            @RequestBody TokenRequestDto tokenRequestDto, HttpServletRequest request) throws CustomAuthenticationEntryPoint {
        LOGGER.info("token 재발급 과정입니다");
        return responseService.getSingleResult(signService.reissue(tokenRequestDto, request));
    }

}
