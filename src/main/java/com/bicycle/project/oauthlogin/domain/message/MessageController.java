package com.bicycle.project.oauthlogin.domain.message;


import com.bicycle.project.oauthlogin.config.RegularException;
import com.bicycle.project.oauthlogin.config.RegularResponse;
import com.bicycle.project.oauthlogin.config.RegularResponseStatus;
import com.bicycle.project.oauthlogin.domain.message.dto.GetMessageReq;
import com.bicycle.project.oauthlogin.domain.message.dto.GetMessageRes;
import com.bicycle.project.oauthlogin.domain.message.dto.WriteMessageReq;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/writeMessage")
    public RegularResponse<String> writeMessage(@RequestBody @Valid WriteMessageReq writeMessageReq){
        try {
            String result = messageService.writeMessage(writeMessageReq);
            return new RegularResponse<>(result);
        }catch (RegularException regularException){
            regularException.printStackTrace();
            return new RegularResponse<>(regularException.getStatus());
        }
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name="X-AUTH-TOKEN", value = "로그인 한 뒤 access token", required=true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value="읽은 아이디 조회")
    @GetMapping("/getNotReads/{userId}")
    public RegularResponse<Long> getNotReads(@PathVariable @Valid Long userId){
        try {

            Long count = messageService.getNotReads(userId);
            return new RegularResponse<>(count);
        }catch (RegularException regularException){
            regularException.printStackTrace();
            return new RegularResponse<>(regularException.getStatus());
        }
    }

    @GetMapping("/getConnectedUser/{userId}")
    public RegularResponse<List<Long>> getConnectedUser(@PathVariable @Valid Long userId){
        try {
            List<Long> result = messageService.getConnectedUser(userId);
            return new RegularResponse<>(result);
        }catch (RegularException regularException){
            regularException.printStackTrace();
            return new RegularResponse<>(regularException.getStatus());
        }
    }

    @PostMapping ("/getMessage")
    public RegularResponse<List<GetMessageRes>> getMessage(@RequestBody @Valid GetMessageReq getMessageReq){
        try {
            List<GetMessageRes> result = messageService.getMessage(getMessageReq);
            return new RegularResponse<>(result);
        }catch (RegularException regularException){
            regularException.printStackTrace();
            return new RegularResponse<>(RegularResponseStatus.REQUEST_ERROR);
        }
    }
}
