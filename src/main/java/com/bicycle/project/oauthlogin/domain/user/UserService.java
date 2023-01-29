package com.bicycle.project.oauthlogin.domain.user;


import com.bicycle.project.oauthlogin.config.RegularException;
import com.bicycle.project.oauthlogin.domain.user.dto.GetUserComReq;
import com.bicycle.project.oauthlogin.domain.user.dto.GetUserComRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.bicycle.project.oauthlogin.config.RegularResponseStatus.REQUEST_ERROR;


@Transactional(readOnly = true)
@Service
public class UserService {

    @Autowired
    private UserDao userDao;


    public List<GetUserComRes> getNote(GetUserComReq getUserComReq) throws RegularException {
        try {
            List<GetUserComRes> result = new ArrayList<>();
            getUserComReq.setPage((getUserComReq.getPage()-1)*11);
            if(getUserComReq.getNoteCategory() == 1){
                result = userDao.getNote1(getUserComReq);

            }else if(getUserComReq.getNoteCategory() == 2){
                result = userDao.getNote2(getUserComReq);
            }else if(getUserComReq.getNoteCategory() == 3){
                result = userDao.getNote3(getUserComReq);
            }

            return result;
        }catch (Exception e){
            e.printStackTrace();
            throw new RegularException(REQUEST_ERROR);
        }

    }


    public int getSumNote(GetUserComReq getUserComReq) {

        int result=0;
        if(getUserComReq.getNoteCategory() == 1){
            result = userDao.getSumNote1(getUserComReq);
        }else if(getUserComReq.getNoteCategory() == 2){
            result = userDao.getSumNote2(getUserComReq);
        }else if(getUserComReq.getNoteCategory() == 3){
            result = userDao.getSumNote3(getUserComReq);
        }
        return result;
    }
}
