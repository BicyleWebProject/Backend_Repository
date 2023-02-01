package com.bicycle.project.oauthlogin.domain.message;


import com.bicycle.project.oauthlogin.config.RegularException;
import com.bicycle.project.oauthlogin.config.RegularResponse;
import com.bicycle.project.oauthlogin.config.RegularResponseStatus;
import com.bicycle.project.oauthlogin.domain.message.dto.WriteMessageReq;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
}
