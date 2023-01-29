package com.bicycle.project.oauthlogin.domain.user;



import com.bicycle.project.oauthlogin.config.RegularException;
import com.bicycle.project.oauthlogin.config.RegularResponse;
import com.bicycle.project.oauthlogin.domain.user.dto.GetUserComReq;
import com.bicycle.project.oauthlogin.domain.user.dto.GetUserComRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @PostMapping("/getNote")
    public RegularResponse<List<GetUserComRes>> getNote(@RequestBody @Valid GetUserComReq getUserComReq){

        try {

                List<GetUserComRes> result = userService.getNote(getUserComReq);
                int sumNote = userService.getSumNote(getUserComReq);


            return new RegularResponse<>(result,sumNote);

        }catch (RegularException regularException){
            regularException.printStackTrace();
            return new RegularResponse<>(regularException.getStatus());
        }
    }

}
