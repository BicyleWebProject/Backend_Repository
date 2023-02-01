package com.bicycle.project.oauthlogin.domain.message;


import com.bicycle.project.oauthlogin.config.RegularException;
import com.bicycle.project.oauthlogin.config.RegularResponseStatus;
import com.bicycle.project.oauthlogin.domain.message.dto.GetMessageReq;
import com.bicycle.project.oauthlogin.domain.message.dto.GetMessageRes;
import com.bicycle.project.oauthlogin.domain.message.dto.WriteMessageReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.springframework.stereotype.Service
@Transactional(readOnly = true)
public class MessageService {

    @Autowired
    private MessageDao messageDao;

    @Transactional
    public String writeMessage(WriteMessageReq writeMessageReq) throws RegularException {

        try {
            messageDao.writeMessage(writeMessageReq);
            return new String("쪽지 등록을 성공하였습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RegularException(RegularResponseStatus.REQUEST_ERROR);
        }
    }


    public Long getNotReads(Long userIdx) throws RegularException {
        try {
            Long count = messageDao.getNotReads(userIdx);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RegularException(RegularResponseStatus.REQUEST_ERROR);
        }
    }


    public List<Long> getConnectedUser(Long userId) throws RegularException {
        try {
            List<Long> result = messageDao.getConnectedUser(userId);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RegularException(RegularResponseStatus.REQUEST_ERROR);
        }
    }


    @Transactional
    public List<GetMessageRes> getMessage(GetMessageReq getMessageReq) throws RegularException {
        try {
            messageDao.updateIsChecked(getMessageReq);
            List<GetMessageRes> result = messageDao.getMessage(getMessageReq);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RegularException(RegularResponseStatus.REQUEST_ERROR);
        }
    }
}
