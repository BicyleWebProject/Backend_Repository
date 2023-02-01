package com.bicycle.project.oauthlogin.domain.deal;


import com.bicycle.project.oauthlogin.config.RegularException;
import com.bicycle.project.oauthlogin.domain.deal.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.bicycle.project.oauthlogin.config.RegularResponseStatus.REQUEST_ERROR;


@Transactional(readOnly = true)
@Service
public class DealService {

    Logger logger = LoggerFactory.getLogger(DealService.class);
    @Autowired
    private DealDao dealDao;


    public List<GetTopListRes> getTopList() throws RegularException {
        try{
            List<GetTopListRes> result = dealDao.getTopList();
            return result;
        }catch (Exception e){
            e.printStackTrace();
           throw new RegularException(REQUEST_ERROR);
        }

    }

    public List<RecentListDto> getRecentList(String userEmail) throws Exception{
        try{
            List<RecentListDto> result = dealDao.getRecentList(userEmail);
            logger.info("getRecentList 데이터 전달");

            return result;
        }catch (Exception e){
            e.printStackTrace();
            throw new RegularException(REQUEST_ERROR);
        }
    }

    //왜 userId가 null로 뜰까요...?
    public List<GetFullRecentListRes> getFullRecentList(Long categoryId) throws RegularException {
        try{
            List<GetFullRecentListRes> result = dealDao.getFullRecentList(categoryId);
            logger.info("FullRecentList 정보전달");
            return result;
        }catch (Exception e){
            e.printStackTrace();
            throw new RegularException(REQUEST_ERROR);
        }
    }
    @Transactional
    public String writeDeal (WritingDealReq writingDealReq, List<String>imageUrls)throws RegularException {
        try {
            writingDealReq.setImageUrl1(imageUrls.get(0));
            if(imageUrls.size()!=1)
                writingDealReq.setImageUrl2(imageUrls.get(1));
            if(imageUrls.size()==3)
                writingDealReq.setImageUrl3(imageUrls.get(2));
            dealDao.writeDeal(writingDealReq);
            return new String("거래글 등록 성공하였습니다.");
        }catch (Exception e){
            e.printStackTrace();
            throw new RegularException(REQUEST_ERROR);
        }
    }

    @Transactional
    public String writeDeal (WritingDealReq writingDealReq)throws RegularException {
        try {
            dealDao.writeDeal(writingDealReq);
            return new String("거래글 등록 성공하였습니다.");
        }catch (Exception e){
            e.printStackTrace();
            throw new RegularException(REQUEST_ERROR);
        }
    }

    public GetDealDetailRes getDealDetail(Long dealId) throws RegularException{
        try {
            GetDealDetailRes result = dealDao.getDealDetail(dealId);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            throw  new RegularException(REQUEST_ERROR);
        }
    }

    @Transactional
    public String pushDealLike(DealLikeReq dealLikeReq) throws RegularException{

        try {
            dealDao.pushDealLike(dealLikeReq);
            return new String("좋아요 등록되었습니다.");
        }catch (Exception exception){
            exception.printStackTrace();
            throw new RegularException(REQUEST_ERROR);
        }
    }

    @Transactional
    public String updateDeal(UpdateDealReq updateDealReq)throws RegularException {
        try {
            dealDao.updateDeal(updateDealReq);
            return new String("거래글 수정 성공하였습니다.");
        }catch (Exception e){
            e.printStackTrace();
            throw new RegularException(REQUEST_ERROR);
        }

    }

    @Transactional
    public String deleteDeal(Long dealId) throws RegularException{
            try {
                dealDao.deleteDealLike(dealId);
                dealDao.deleteDeal(dealId);
                return new String("거래글이 삭제되었습니다.");
            }catch (Exception e){
                e.printStackTrace();
                throw new RegularException(REQUEST_ERROR);
            }

    }
}
