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

    @GetMapping("/find/{userEmail}") //?????? ??????
    public RegularResponse<String> findUserByEmail(@PathVariable @Valid String userEmail){
        GetUserComReq getUserComReq;
        if(userService.isThereUserEmail(userEmail)){
            return new RegularResponse<>(new String("????????? ?????????????????? ?????? ???????????? ????????????"));
        }

        return new RegularResponse<>(new String("????????? ?????????????????? ???????????? ?????? ????????????"));
    }

    /*
    ???????????? ?????? ?????????, access Token??? ?????? ?????? ??????(user ?????????)??? ????????????!
     */
    @PostMapping("/updateUser") //@PutMapping
    public RegularResponse<String> updateUser(@RequestPart(value="multipartFile")MultipartFile multipartFile,
                                              @RequestPart(value="UpdateUser") String UpdateUserDto,
                                              HttpServletRequest request) throws RegularException {

        String userPk = tokenProvider.getUserPk(tokenProvider.resolveToken(request));
        if(!chkToken(request)){
            logger.info("you{}", userPk); //userIdx ??????
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
            return new RegularResponse<>(new String("???????????? ????????? ?????????????????????."));

        }catch (Exception e){
            e.printStackTrace();
            return new RegularResponse<>(new String("???????????? ????????? ?????????????????????."));
        }
    }



    /*
        31.??????????????????, ??????
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
    ???????????? ?????????, ??????????????? X-AUTH-TOKEN??? ?????? ??????(access Token??? ?????? ?????? ??????)??? ??????
    ????????? ????????? ????????? ?????? ???????????? getName()??? ?????? ?????? ???????????? ??????
    ????????? ??? ?????? ????????? ???????????? ????????? ????????? ???????????? ?????? - ??????????????? ?????? ??????
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="X-AUTH-TOKEN", value = "????????? ??? ??? access token", required=true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value="?????? ??????")
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
