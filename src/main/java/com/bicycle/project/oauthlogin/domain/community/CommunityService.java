package com.bicycle.project.oauthlogin.domain.community;



import com.bicycle.project.oauthlogin.config.RegularException;
import com.bicycle.project.oauthlogin.config.RegularResponseStatus;
import com.bicycle.project.oauthlogin.domain.comment.CommentDao;
import com.bicycle.project.oauthlogin.domain.community.dto.*;
import com.bicycle.project.oauthlogin.domain.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.bicycle.project.oauthlogin.config.RegularResponseStatus.REQUEST_ERROR;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class CommunityService {

    @Autowired
    public CommunityDao communityDao;


    private final S3Uploader s3Uploader;

    @Autowired
    public CommentDao commentDao;

    Logger logger = LoggerFactory.getLogger(CommunityService.class);
    public List<TopListDto> getTopList(){
        List<TopListDto> getTopList = communityDao.getTopList();
        return getTopList;
    }

    public List<RecentListDto> getRecentList(){
        List<RecentListDto> getRecentList = communityDao.getRecentList();
        return getRecentList;
    }

    public List<GetUserRecentList> getUserRecentList() throws RegularException {
        try{
            List<GetUserRecentList> result = communityDao.getUserRecentList();
            logger.info("CommunityService test!");

            return result;
        }catch (Exception e){
            e.printStackTrace();
            throw new RegularException(REQUEST_ERROR);
        }
    }

    public List<GetSearchByWriter> getSearchByWriter(String userEmail) throws RegularException{
        try{
            List<GetSearchByWriter> result = communityDao.getSearchByWriter(userEmail);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            throw new RegularException(REQUEST_ERROR);
        }
    }

    @Transactional
    public void writeCommunity(CommunityWritingReq communityWritingReq) {
        communityWritingReq.setStatus("Y");
        communityDao.writeCommunity(communityWritingReq);

    }

    public CommunityDetailRes getCommunityDetail(Integer communityId) {
        CommunityDetailRes result = communityDao.getCommunityDetail(communityId);
        return result;
    }

    @Transactional
    public String uploadS3image(MultipartFile multipartFile, Long userId) throws RegularException {
        try {

            String imagePath1 = s3Uploader.upload1(multipartFile, "userIdx" + String.valueOf(userId));
            return imagePath1;
        }catch(NullPointerException nullPointerException){
            nullPointerException.printStackTrace();
            return new String();
        }catch(Exception exception) {
            exception.printStackTrace();
            throw new RegularException(REQUEST_ERROR);
        }
    }

    @Transactional
    public List<String> uploadS3images(List<MultipartFile> multipartFileList, Long userIdx) throws RegularException {
        try {

            List<String> imagePath1 = s3Uploader.upload(multipartFileList, "userIdx" + String.valueOf(userIdx));
            return imagePath1;
        }catch(NullPointerException nullPointerException){
            return new ArrayList<>();
        }catch(Exception exception) {
            exception.printStackTrace();
            throw new RegularException(REQUEST_ERROR);
        }


    }


    @Transactional
    public String pushCommunityLike(CommunityLikeReq communityLikeReq) throws RegularException{
        try {
            communityDao.pushCommunityLike(communityLikeReq);
            return new String("커뮤니티 좋아요 등록에 성공하였습니다.");
        }catch (Exception e){
            e.printStackTrace();
            throw new RegularException(REQUEST_ERROR);
        }

    }

    @Transactional
    public String updateCommunity(UpdateCommunityReq updateCommunityReq) throws RegularException {
        try {
            communityDao.updateCommunity(updateCommunityReq);
            return new String("커뮤니티 글이 수정되었습니다.");
        }catch (Exception e){
            e.printStackTrace();
            throw new RegularException(REQUEST_ERROR);
        }
    }

    @Transactional
    public String deleteCommunity(Long communityId) throws RegularException{
        try {
            communityDao.deleteCommunityLike(communityId);
            commentDao.deleteReply(communityId);
            commentDao.deleteComment(communityId);
            communityDao.deleteCommunity(communityId);
            return new String("커뮤니티 글이 삭제되었습니다.");
        }catch (Exception e){
            e.printStackTrace();
            throw new RegularException(REQUEST_ERROR);
        }

    }
}
