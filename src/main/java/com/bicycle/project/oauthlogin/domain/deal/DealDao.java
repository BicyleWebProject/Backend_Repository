package com.bicycle.project.oauthlogin.domain.deal;

import com.bicycle.project.oauthlogin.domain.deal.dto.*;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DealDao {

    public List<GetTopListRes> getTopList();

    //그냥 거레 게시글 자체를 가져오면 됨. 10개
    public List<GetFullRecentListRes> getFullRecentList(@Param("categoryId") Long categoryId);

    public List<RecentListDto> getRecentList(@Param("userEmail") String userEmail);

    void writeDeal(WritingDealReq writingDealReq);

    GetDealDetailRes getDealDetail(Long dealId);


    void pushDealLike(DealLikeReq dealLikeReq);


    void updateDeal(UpdateDealReq updateDealReq);

    void deleteDealLike(Long dealId);

    void deleteDeal(Long dealId);
}
