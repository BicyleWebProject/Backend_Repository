package com.bicycle.project.oauthlogin.domain.notice;



import com.bicycle.project.oauthlogin.domain.notice.dto.NoticeList;
import com.bicycle.project.oauthlogin.domain.notice.dto.NoticeListReq;
import com.bicycle.project.oauthlogin.domain.notice.dto.NoticeWritingReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeDao {

    public List<NoticeList> getBoard(NoticeListReq noticeListReq);

    public int getSumBoard(NoticeListReq noticeListReq);

    public NoticeList getBoardDetail(Long boardId);

    void writeNotice(NoticeWritingReq noticeWritingReq);
}
