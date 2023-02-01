package com.bicycle.project.oauthlogin.domain.notice;



import com.bicycle.project.oauthlogin.config.RegularException;
import com.bicycle.project.oauthlogin.config.RegularResponseStatus;
import com.bicycle.project.oauthlogin.domain.notice.dto.NoticeList;
import com.bicycle.project.oauthlogin.domain.notice.dto.NoticeListReq;
import com.bicycle.project.oauthlogin.domain.notice.dto.NoticeWritingReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class NoticeService {

    @Autowired
    private NoticeDao noticeDao;

    @Transactional
    public List<NoticeList> getBoard(NoticeListReq noticeListReq){

        noticeListReq.setPage((noticeListReq.getPage()-1)*11);
        List<NoticeList> getBoard = noticeDao.getBoard(noticeListReq);

        return getBoard;
    }

    @Transactional
    public Integer getSumBoard(NoticeListReq noticeListReq){
        Integer result = noticeDao.getSumBoard(noticeListReq);
        return result;
    }


    public NoticeList getBoardDetail(Long boardId) throws RegularException {
        try {
                NoticeList result = noticeDao.getBoardDetail(boardId);
                return result;
        }catch (Exception e){
            e.printStackTrace();
            throw new RegularException(RegularResponseStatus.REQUEST_ERROR);
        }
    }

    @Transactional
    public String writeNotice(NoticeWritingReq noticeWritingReq) throws RegularException{
        try {
            noticeDao.writeNotice(noticeWritingReq);
            return new String("공지글 등록 성공하였습니다.");
        }catch (Exception e){
            e.printStackTrace();
            throw new RegularException(RegularResponseStatus.REQUEST_ERROR);
        }

    }
}
