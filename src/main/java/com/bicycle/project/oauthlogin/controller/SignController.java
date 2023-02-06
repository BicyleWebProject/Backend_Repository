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
            @ApiParam(value="token-재발급 dto", required = true)
            @RequestBody TokenRequestDto tokenRequestDto) throws CustomAuthenticationEntryPoint {
        LOGGER.info("token 재발급 과정입니다");
        return responseService.getSingleResult(signService.reissue(tokenRequestDto));
    }



//    private final SignServiceImpl userService;

//    @Autowired
//    public SignController(SignServiceImpl userService){
//        this.userService = userService;
//    }

//    @PostMapping(value = "/sign-in")
//    public SignInResultDto signIn(
//            @ApiParam(value = "ID", required = true) @RequestParam String userEmail,
//            @ApiParam(value = "Password" , required = true) @RequestParam String password)
//            throws RuntimeException{
//        LOGGER.info("[SignIn] 로그인 시도중입니다. id : {}, pw : ****", userEmail);
//        SignInResultDto signInResultDto = userService.signIn(userEmail, password);
//
//        if(signInResultDto.getCode() == 0){
//            LOGGER.info("[signIn] 정상적으로 로그인 되었습니다. id : {}, token : {} ", userEmail, signInResultDto.getToken());
//        }
//        return signInResultDto;
//    }
//
//    @PostMapping(value = "/sign-up")
//    public SignUpResultDto signUp(
//            @ApiParam(value = "userEmail", required = true) @RequestParam String userEmail,
//            @ApiParam(value = "password", required = true) @RequestParam String password,
//            @ApiParam(value = "name", required = true) @RequestParam String name,
//            @ApiParam(value = "role", required = true) @RequestParam String role) {
//        LOGGER.info("[SignUp] 회원가입을 수행합니다. id : {} , password : ****, name : {}, role : {} ", userEmail, name, role);
//        SignUpResultDto signUpResultDto = userService.signUp(userEmail, password, name, role);
//
//        LOGGER.info("[signUp] 회원가입이 완료되었습니다. id : {}", userEmail);
//        return signUpResultDto;
//    }
//
//    @GetMapping(value = "/exception")
//    public void exceptionTest() throws RuntimeException{
//        throw new RuntimeException("접근이 금지되었습니다.");
//    }
//
//    @ExceptionHandler(value = RuntimeException.class)
//    public ResponseEntity<Map<String, String>> ExceptionHandler(RuntimeException e){
//        HttpHeaders responseHeaders = new HttpHeaders();
//        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
//
//        LOGGER.error("ExceptionHandler 호출", e.getCause(), e.getMessage());
//
//        Map<String, String> map = new HashMap<>();
//        map.put("error type", httpStatus.getReasonPhrase());
//        map.put("code", "400");
//        map.put("message", "에러 발생함");
//
//        return new ResponseEntity<>(map, responseHeaders, httpStatus);
//
//    }
}
