package com.bicycle.project.oauthlogin.domain.community;



import com.bicycle.project.oauthlogin.domain.community.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommunityDao {

    public List<TopListDto> getTopList();

    public List<RecentListDto> getRecentList();

    public List<GetUserRecentList> getUserRecentList(CommunityListReq communityListReq);

    public int getSumBoard(CommunityListReq communityListReq);

    public List<GetSearchByWriter> getSearchByWriter(String userEmail);

    public List<GetSearchByContent> getSearchByContent(String content);

    public void writeCommunity(CommunityWritingReq communityWritingReq);

    CommunityDetailRes getCommunityDetail(Integer communityId);

    void pushCommunityLike(CommunityLikeReq communityLikeReq);

    void updateCommunity(UpdateCommunityReq updateCommunityReq);

    void deleteCommunity(Long communityId);


    void deleteCommunityLike(Long communityId);
}
