package com.bicycle.project.oauthlogin.domain.community;



import com.bicycle.project.oauthlogin.config.RegularException;
import com.bicycle.project.oauthlogin.config.RegularResponse;
import com.bicycle.project.oauthlogin.domain.community.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/community")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @GetMapping("/ctest")
    public String ctest(){
        return "커뮤니티테스트";
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


    @ResponseBody
    @PostMapping("/writing")
    public RegularResponse<String> writeCommunity(@RequestPart(value = "communityWritingReq") CommunityWritingReq communityWritingReq,
                                                  @RequestPart(value = "images")MultipartFile multipartFile){

        try {
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
    public RegularResponse<String> updateCommunity(@RequestBody @Valid UpdateCommunityReq updateCommunityReq){
        try {
            String result = communityService.updateCommunity(updateCommunityReq);
            return new RegularResponse<>(result);
        }catch (RegularException regularException){
            regularException.printStackTrace();
            return new RegularResponse<>(regularException.getStatus());
        }
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
