package com.bicycle.project.oauthlogin.domain.deal;



import com.bicycle.project.oauthlogin.config.RegularException;
import com.bicycle.project.oauthlogin.config.RegularResponse;
import com.bicycle.project.oauthlogin.config.RegularResponseStatus;
import com.bicycle.project.oauthlogin.domain.community.CommunityService;
import com.bicycle.project.oauthlogin.domain.deal.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/deal")
public class DealController {
    Logger logger =  LoggerFactory.getLogger(DealController.class);

    @Autowired
    private CommunityService communityService;

    @Autowired
    private DealService dealService;

    @GetMapping("/getTopList")
    public RegularResponse<List<GetTopListRes>> getTopList(){
        try {
            List<GetTopListRes> result = dealService.getTopList();
            return new RegularResponse<>(result);
        }catch (RegularException regularException){
            return new RegularResponse<>(regularException.getStatus());
        }

    }

    @PostMapping("/getFullRecentList")
    public RegularResponse<List<GetFullRecentListRes>> getFullRecentList(@RequestBody @Valid RecentListReq recentListReq){
        try{
            List<GetFullRecentListRes> result = dealService.getFullRecentList(recentListReq);
            Integer getSumDeal = dealService.getSumBoard(recentListReq);
            for(GetFullRecentListRes x : result){
                logger.info("{}123", x);
            }
            return new RegularResponse<>(result, getSumDeal);
        }catch (RegularException e){
            e.printStackTrace();
            return new RegularResponse<>(new ArrayList<GetFullRecentListRes>());
        }
    }

    @GetMapping("/getRecentList/{userEmail}")
    public RegularResponse<List<RecentListDto>> getRecentList(@PathVariable String userEmail){
        try{

            List<RecentListDto> result = dealService.getRecentList(userEmail);

            return new RegularResponse<>(result);
        }catch (RegularException e){
            return new RegularResponse<>(e.getStatus());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @PostMapping("/writeDeal")
    public RegularResponse<String> writeDeal(@RequestPart(value = "writingDealReq") String writingDealReqDto,
                                             @RequestPart(value = "images")List<MultipartFile> multipartFiles){

        try{
            WritingDealReq writingDealReq =  new ObjectMapper().readValue(writingDealReqDto,WritingDealReq.class);
            List<String> imageUrls = new ArrayList<>();
            String result = null;
            if(!multipartFiles.get(0).getOriginalFilename().equals("")){
               imageUrls= communityService.uploadS3images(multipartFiles, writingDealReq.getUserId());
                result = dealService.writeDeal(writingDealReq,imageUrls);
            }else{
                imageUrls = null;
                result = dealService.writeDeal(writingDealReq);
            }

            return new RegularResponse<>(result);
        }catch (RegularException regularException){
            return new RegularResponse<>(regularException.getStatus());
        }catch (Exception e){
            e.printStackTrace();
            return new RegularResponse<>(new String("커뮤니티글 등록 실패"));
        }
    }

    @GetMapping("/getDealDetail/{dealId}")
    public RegularResponse<GetDealDetailRes> getDealDetail(@PathVariable @Valid Long dealId){
        try {
            GetDealDetailRes result = dealService.getDealDetail(dealId);
            return new RegularResponse<>(result);
        }catch (RegularException regularException){
            regularException.printStackTrace();
            return new RegularResponse<>(RegularResponseStatus.REQUEST_ERROR);
        }
    }

    @PostMapping("/pushDealLike")
    public RegularResponse<String> pushDealLike(@RequestBody @Valid DealLikeReq dealLikeReq){
        try {
            String result = dealService.pushDealLike(dealLikeReq);
            return new RegularResponse<>(result);
        }catch (Exception exception){
            exception.printStackTrace();
            return new RegularResponse<>(new String("좋아요 등록 실패했습니다."));
        }
    }

    @PatchMapping("/updateDeal")
    public RegularResponse<String> updateDeal(@RequestBody @Valid UpdateDealReq updateDealReq){
        try {
            String result = dealService.updateDeal(updateDealReq);
            return new RegularResponse<>(result);
        }catch (RegularException regularException){
            regularException.printStackTrace();
            return new RegularResponse<>(RegularResponseStatus.REQUEST_ERROR);
        }
    }

    @DeleteMapping("/deleteDeal/{dealId}")
    public RegularResponse<String> deleteDeal(@PathVariable @Valid Long dealId){
        try {
            String result = dealService.deleteDeal(dealId);
            return new RegularResponse<>(result);
        }catch (RegularException regularException){
            regularException.printStackTrace();
            return new RegularResponse<>(RegularResponseStatus.REQUEST_ERROR);
        }
    }

}
