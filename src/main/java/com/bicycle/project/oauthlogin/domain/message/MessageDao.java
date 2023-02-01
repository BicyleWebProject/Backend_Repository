package com.bicycle.project.oauthlogin.domain.message;

import com.bicycle.project.oauthlogin.domain.message.dto.WriteMessageReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageDao {
    void writeMessage(WriteMessageReq writeMessageReq);

    Long getNotReads(Long userIdx);
}
