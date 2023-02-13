package com.bicycle.project.oauthlogin.domain.user;



import com.bicycle.project.oauthlogin.common.CommonResult;
import com.bicycle.project.oauthlogin.common.SingleResult;
import com.bicycle.project.oauthlogin.config.RegularException;
import com.bicycle.project.oauthlogin.config.RegularResponse;
import com.bicycle.project.oauthlogin.config.RegularResponseStatus;
import com.bicycle.project.oauthlogin.config.security.TokenProvider;
import com.bicycle.project.oauthlogin.data.entity.User;
import com.bicycle.project.oauthlogin.domain.user.dto.*;
import com.bicycle.project.oauthlogin.repository.UserRepository;
import com.bicycle.project.oauthlogin.service.ResponseService;
import com.bicycle.project.oauthlogin.utils.SecurityUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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

    private SecurityUtil securityUtil;

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
    @PostMapping("/updateUser") //@PutMapping
    public RegularResponse<String> updateUser(@RequestPart(value="multipartFile")MultipartFile multipartFile,
                                              @RequestPart(value="UpdateUser") String UpdateUserDto,
                                              HttpServletRequest request) throws RegularException {

        String userPk = tokenProvider.getUserPk(tokenProvider.resolveToken(request));
        if(!chkToken(request)){
            logger.info("you{}", userPk); //userIdx 반환
            throw new RegularException(RegularResponseStatus.REQUEST_ERROR);
        }
        try{
            UpdateUser updateUser = new ObjectMapper().readValue(UpdateUserDto, UpdateUser.class);
            String userImgUrl = null;
            if(!multipartFile.getOriginalFilename().equals("")){
                userImgUrl = userService.uploadS3image(multipartFile, Long.valueOf(tokenProvider.getUserPk(tokenProvider.resolveToken(request))));
                updateUser.setUserImgUrl(userImgUrl);
            }else{
                updateUser.setUserImgUrl(null);
            }
            UserRequestDto userRequestDto = UserRequestDto.builder()
                    .username(updateUser.getNewUsername())
                    .userImgUrl(updateUser.getUserImgUrl())
                    .location(updateUser.getLocation())
                    .interestedAt(updateUser.getInterestedAt())
                    .build();

            userService.update(Long.valueOf(userPk), updateUser);
            return new RegularResponse<>(new String("유저정보 변경이 완료되었습니다."));

        }catch (Exception e){
            e.printStackTrace();
            return new RegularResponse<>(new String("유저정보 변경에 실패하였습니다."));
        }
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
    그러면 토큰만 받아서 내부 로직에서 getName()을 꺼내 그냥 비교없이 사용
    그러면 그 유저 자체를 쿼리에서 받아서 변환후 변경하는 방법 - 보안적으로 제일 깔끔
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="X-AUTH-TOKEN", value = "로그인 한 뒤 access token", required=true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value="회원 삭제")
    @DeleteMapping(value="/user/deleteUser")
    public CommonResult delete(HttpServletRequest request) throws RegularException {
//        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!chkToken(request)){
            logger.info("123{}", tokenProvider.getUserPk(tokenProvider.resolveToken(request)));
            throw new RegularException(RegularResponseStatus.REQUEST_ERROR);
        }

        logger.warn("warning!!!!!!!!!!!!!!!!");
        userRepository.deleteByUserEmail(tokenProvider.getUserPk(tokenProvider.resolveToken(request)));
        return responseService.getSuccessResult();
    }

    private boolean chkToken(HttpServletRequest request){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return tokenProvider.validationToken(tokenProvider.resolveToken(request));
    }
}
