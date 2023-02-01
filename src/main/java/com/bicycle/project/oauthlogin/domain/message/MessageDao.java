package com.bicycle.project.oauthlogin.domain.message;

import com.bicycle.project.oauthlogin.domain.message.dto.GetMessageReq;
import com.bicycle.project.oauthlogin.domain.message.dto.GetMessageRes;
import com.bicycle.project.oauthlogin.domain.message.dto.WriteMessageReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageDao {
    void writeMessage(WriteMessageReq writeMessageReq);

    Long getNotReads(Long userIdx);

    List<Long> getConnectedUser(Long userId);


    List<GetMessageRes> getMessage(GetMessageReq getMessageReq);

    void updateIsChecked(GetMessageReq getMessageReq);
}
