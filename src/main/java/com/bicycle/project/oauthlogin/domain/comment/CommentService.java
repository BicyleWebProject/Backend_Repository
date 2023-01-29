package com.bicycle.project.oauthlogin.domain.comment;



import com.bicycle.project.oauthlogin.domain.comment.dto.GetCommentsRes;
import com.bicycle.project.oauthlogin.domain.comment.dto.GetReplyReq;
import com.bicycle.project.oauthlogin.domain.comment.dto.GetReplyRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentDao commentDao;

    @Transactional
    public List<GetCommentsRes> getComments(int commentIdx) {

        List<GetCommentsRes> result = commentDao.getComments(commentIdx);
        return result;
    }

    @Transactional
    public List<GetReplyRes> getReplies(GetReplyReq getReplyReq) {
        List<GetReplyRes> result = commentDao.getReplies(getReplyReq);
        return  result;

    }
}
