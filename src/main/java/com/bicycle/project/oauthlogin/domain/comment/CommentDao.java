package com.bicycle.project.oauthlogin.domain.comment;


import com.bicycle.project.oauthlogin.domain.comment.dto.GetCommentsRes;
import com.bicycle.project.oauthlogin.domain.comment.dto.GetReplyReq;
import com.bicycle.project.oauthlogin.domain.comment.dto.GetReplyRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentDao {
    List<GetCommentsRes> getComments(int commentIdx);


    List <GetReplyRes> getReplies(GetReplyReq getReplyReq);

    void deleteReply(Long communityId);

    void deleteComment(Long communityId);
}
