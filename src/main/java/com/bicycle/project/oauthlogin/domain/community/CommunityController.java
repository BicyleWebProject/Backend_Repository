package com.bicycle.project.oauthlogin.domain.community;



import com.bicycle.project.oauthlogin.config.RegularException;
import com.bicycle.project.oauthlogin.config.RegularResponse;
import com.bicycle.project.oauthlogin.config.security.TokenProvider;
import com.bicycle.project.oauthlogin.domain.community.dto.*;
import com.bicycle.project.oauthlogin.exception.TokenValidFailedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    private final TokenProvider tokenProvider;

    @GetMapping("/ctest")
    public String ctest(){
        return "커뮤니티테스트 젠킨스 수정2";
    }

    @GetMapping("/topList")
    public RegularResponse<List<TopListDto>> topList(){
        try {
            List<TopListDto> getTopList = communityService.getTopList();
            return new RegularResponse<>(getTopList);

        }catch (Exception e){
            e.printStackTrace();
           return new RegularResponse<>(new ArrayList<>());
        }


    }

    /*
    최근 커뮤니티게시글 조회
     */
    @GetMapping("/recentWrite")
    public RegularResponse<List<RecentListDto>> recentList(){
        try{
            List<RecentListDto> getRecentList = communityService.getRecentList();
            return new RegularResponse<>(getRecentList);
        }catch(Exception e){
            e.printStackTrace();
            return new RegularResponse<>(new ArrayList<>());
        }
    }

    @PostMapping("/userRecentList")
    public RegularResponse<List<GetUserRecentList>> getUserRecentList(@RequestBody @Valid CommunityListReq communityListReq){
        try{
            List<GetUserRecentList> getUserRecentList = communityService.getUserRecentList(communityListReq);
            Integer getSumBoard = communityService.getSumBoard(communityListReq);
            return new RegularResponse<>(getUserRecentList);
        }catch (Exception e){
            e.printStackTrace();
            return new RegularResponse<>(new ArrayList<>());
        }
    }

//    @ResponseBody
//    @PostMapping("/writing")
//    public RegularResponse<String> writeCommunity(@RequestPart(value = "communityWritingReq") CommunityWritingReq communityWritingReq,
//                                                  @RequestPart(value = "images")MultipartFile multipartFile){
//
//        try {
//            String imageUrls=null;
//            if(!multipartFile.getOriginalFilename().equals("")) {
//                imageUrls = communityService.uploadS3image(multipartFile, communityWritingReq.getUserId());
//                communityWritingReq.setCommunityImageUrl(imageUrls);
//            }else
//                communityWritingReq.setCommunityImageUrl(null);
//            communityService.writeCommunity(communityWritingReq);
//            return new RegularResponse<>(new String("게시글 등록에 성공하였습니다. 등록된 이미지url: "+imageUrls));
//        }catch (Exception e){
//            e.printStackTrace();
//            return new RegularResponse<>(new String("게시글 등록에 실패하였습니다."));
//        }
//    }

    @PostMapping("/writing")
    public RegularResponse<String> writeCommunity(@RequestPart(value = "multipartFile")MultipartFile multipartFile,
                                                  @RequestPart(value = "communityWritingReqDto") String communityWritingReqDto){
        try {
            CommunityWritingReq communityWritingReq = new ObjectMapper().readValue(communityWritingReqDto, CommunityWritingReq.class);
            String imageUrls=null;
            if(!multipartFile.getOriginalFilename().equals("")) {
                imageUrls = communityService.uploadS3image(multipartFile, communityWritingReq.getUserId());
                communityWritingReq.setCommunityImageUrl(imageUrls);
            }else
                communityWritingReq.setCommunityImageUrl(null);
            communityService.writeCommunity(communityWritingReq);
            return new RegularResponse<>(new String("게시글 등록에 성공하였습니다. 등록된 이미지url: "+imageUrls));
        }catch (Exception e){
            e.printStackTrace();
            return new RegularResponse<>(new String("게시글 등록에 실패하였습니다."));
        }
    }


    @GetMapping("/getCommunityDetail/{communityId}")
    public RegularResponse<CommunityDetailRes> getCommunityDetail(@PathVariable Integer communityId){

        try {
            CommunityDetailRes result = communityService.getCommunityDetail(communityId);
            return new RegularResponse<>(result);
        }catch (Exception e){
            e.printStackTrace();
            return new RegularResponse<>(new CommunityDetailRes());
        }
    }

    @GetMapping("/getSearchByWriter/{userEmail}")
    public RegularResponse<List<GetSearchByWriter>> getSearchByWriter(@PathVariable String userEmail){
        try{
            List<GetSearchByWriter> result = communityService.getSearchByWriter(userEmail);
            return new RegularResponse<>(result);
        }catch (Exception e){
            e.printStackTrace();
            return new RegularResponse<>(new ArrayList<>());
        }
    }

    @GetMapping("/getSearchByContent/{content}")
    public RegularResponse<List<GetSearchByContent>> getSearchByContent(@PathVariable String content){
        try{
            List<GetSearchByContent> result = communityService.getSearchByContent(content);
            return new RegularResponse<>(result);
        }catch (Exception e){
            e.printStackTrace();
            return new RegularResponse<>(new ArrayList<>());
        }
    }

    @PostMapping("/pushCommunityLike")
    public RegularResponse<String> pushCommunityLike(@RequestBody @Valid CommunityLikeReq communityLikeReq){

        try {
            String result = communityService.pushCommunityLike(communityLikeReq);
            return new RegularResponse<>(result);
        }catch (RegularException regularException){
            regularException.printStackTrace();
            return new RegularResponse<>(regularException.getStatus());
        }
    }

    @PatchMapping("/updateCommunity")
    public RegularResponse<String> updateCommunity(@RequestBody @Valid UpdateCommunityReq updateCommunityReq, HttpServletRequest request){
        if (!chkToken(request)) throw new TokenValidFailedException();
        try {
            String result = communityService.updateCommunity(updateCommunityReq);
            return new RegularResponse<>(result);
        }catch (RegularException regularException){
            regularException.printStackTrace();
            return new RegularResponse<>(regularException.getStatus());
        }
    }

    private boolean chkToken(HttpServletRequest request){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return tokenProvider.validationToken(tokenProvider.resolveToken(request));
    }

    @DeleteMapping("/deleteCommunity/{communityId}")
    public RegularResponse<String> deleteCommunity(@PathVariable @Valid Long communityId){
        try {
            String result = communityService.deleteCommunity(communityId);
            return new RegularResponse<>(result);
        }catch (RegularException regularException){
            regularException.printStackTrace();
            return new RegularResponse<>(regularException.getStatus());
        }
    }

}
