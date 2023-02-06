package com.bicycle.project.oauthlogin.domain.user;



import com.bicycle.project.oauthlogin.common.CommonResult;
import com.bicycle.project.oauthlogin.common.SingleResult;
import com.bicycle.project.oauthlogin.config.RegularException;
import com.bicycle.project.oauthlogin.config.RegularResponse;
import com.bicycle.project.oauthlogin.config.security.TokenProvider;
import com.bicycle.project.oauthlogin.data.entity.User;
import com.bicycle.project.oauthlogin.domain.user.dto.*;
import com.bicycle.project.oauthlogin.repository.UserRepository;
import com.bicycle.project.oauthlogin.service.ResponseService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    private final UserRepository userRepository;
    private final ResponseService responseService;

    private final TokenProvider tokenProvider;

    @Autowired
    private UserDao userDao;

    @PostMapping("/getNote")
    public RegularResponse<List<GetUserComRes>> getNote(@RequestBody @Valid GetUserComReq getUserComReq){

        try {

                List<GetUserComRes> result = userService.getNote(getUserComReq);
                int sumNote = userService.getSumNote(getUserComReq);


            return new RegularResponse<>(result,sumNote);

        }catch (RegularException regularException){
            regularException.printStackTrace();
            return new RegularResponse<>(regularException.getStatus());
        }
    }

    @GetMapping("/find/{userEmail}") //회원 검색
    public RegularResponse<String> findUserByEmail(@PathVariable @Valid String userEmail){
        GetUserComReq getUserComReq;
        if(userService.isThereUserEmail(userEmail)){
            return new RegularResponse<>(new String("조회한 이메일주소가 이미 가입되어 있습니다"));
        }

        return new RegularResponse<>(new String("조회한 이메일주소가 가입되어 있지 않습니다"));
    }

    /*
    회원정보 수정 메서드, access Token이 갖고 있는 경우(user 인경우)만 수정가능!
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="X-AUTH-TOKEN", value = "로그인 한 뒤 access token", required=true, dataType = "String", paramType = "header")
    })
    @PutMapping("/updateUser/{userEmail}/{newUsername}")
    public RegularResponse<String> updateUser(@PathVariable @Valid String userEmail, @PathVariable String newUsername) throws RegularException {

        logger.info("userRequestDto 전");

        UserRequestDto userRequestDto = UserRequestDto.builder()
                .username(newUsername)
                .build();
        logger.info("userRequestDto 후, useService 전");
        logger.info("userEmail : {}", userEmail);
        logger.info("newUsername : {}", newUsername);
        userService.update(userEmail, userRequestDto, newUsername);
        logger.info("update 이후");
        return new RegularResponse<>(new String("유저 이름 변경이 완료되었습니다. {newUsername}"));

    }

    /*
        31.상대정보조회, 학준
     */
    @GetMapping("/otherInfo/{userId}")
    public RegularResponse<GetOtherRes> getOthers(@PathVariable @Valid Long userId){
        try {
            GetOtherRes result = userService.getOthers(userId);
            return new RegularResponse<>(result);
        }catch (RegularException regularException){
            regularException.printStackTrace();
            return new RegularResponse<>(new GetOtherRes());
        }
    }


    /*
    회원탈퇴 메서드, 마찬가지로 X-AUTH-TOKEN이 있는 경우(access Token을 갖고 있는 경우)만 가능
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="X-AUTH-TOKEN", value = "로그인 한 뒤 access token", required=true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value="회원 삭제")
    @DeleteMapping(value="/user/{userEmail}")
    public CommonResult delete(
            @ApiParam(value="회원가입한 id", required=true) @PathVariable String userEmail){
        userRepository.deleteByUserEmail(userEmail);
        return responseService.getSuccessResult();
    }
}
