package com.bicycle.project.oauthlogin.domain.user;


import com.bicycle.project.oauthlogin.config.RegularException;
import com.bicycle.project.oauthlogin.data.entity.User;
import com.bicycle.project.oauthlogin.domain.user.dto.GetUserComReq;
import com.bicycle.project.oauthlogin.domain.user.dto.GetUserComRes;
import com.bicycle.project.oauthlogin.domain.user.dto.UserRequestDto;
import com.bicycle.project.oauthlogin.domain.user.dto.UserResponseDto;
import com.bicycle.project.oauthlogin.exception.CUserNotFoundException;
import com.bicycle.project.oauthlogin.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.bicycle.project.oauthlogin.config.RegularResponseStatus.REQUEST_ERROR;


@Transactional(readOnly = true)
@Service
@Slf4j
@AllArgsConstructor
public class UserService {

    @Autowired
    private UserDao userDao;

    private UserRepository userRepository;

    @Transactional
    public Long save(UserRequestDto userDto){
        User saved = userRepository.save(userDto.toEntity());
        return saved.getUserIdx();
    }

    @Transactional(readOnly = true)
    public UserResponseDto findByIdx(Long userIdx){
        User user = userRepository.findByUserIdx(userIdx)
                .orElseThrow(CUserNotFoundException::new);
        return new UserResponseDto(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDto findByUserEmail(String userEmail){
        User user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(CUserNotFoundException::new);
        return new UserResponseDto(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> findAllUser(){
        return userRepository.findAll()
                .stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long update(Long userIdx, UserRequestDto userRequestDto){
        User modifiedUser = userRepository.findByUserIdx(userIdx)
                .orElseThrow(CUserNotFoundException::new);
        return userIdx;
    }

    @Transactional
    public void deleteUser(Long userIdx){
        userRepository.deleteById(userIdx);
    }


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
