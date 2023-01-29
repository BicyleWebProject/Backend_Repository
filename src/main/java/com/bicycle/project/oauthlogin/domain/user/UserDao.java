package com.bicycle.project.oauthlogin.domain.user;


import com.bicycle.project.oauthlogin.domain.user.dto.GetUserComReq;
import com.bicycle.project.oauthlogin.domain.user.dto.GetUserComRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface UserDao {
    public List<GetUserComRes> getNote1(GetUserComReq getUserComReq);

    public List<GetUserComRes> getNote2(GetUserComReq getUserComReq);

    public List<GetUserComRes> getNote3(GetUserComReq getUserComReq);

    public int getSumNote1(GetUserComReq getUserComReq);

    public int getSumNote2(GetUserComReq getUserComReq);

    public int getSumNote3(GetUserComReq getUserComReq);
}
