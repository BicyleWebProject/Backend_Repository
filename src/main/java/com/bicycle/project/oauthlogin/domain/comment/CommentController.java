package com.bicycle.project.oauthlogin.domain.comment;


import com.bicycle.project.oauthlogin.config.RegularResponse;
import com.bicycle.project.oauthlogin.domain.comment.dto.GetCommentsRes;
import com.bicycle.project.oauthlogin.domain.comment.dto.GetReplyReq;
import com.bicycle.project.oauthlogin.domain.comment.dto.GetReplyRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;


    @GetMapping("/getComments/{commentIdx}")
    public RegularResponse<List<GetCommentsRes>> getComments(@PathVariable @Valid int commentIdx){
        try{
            List<GetCommentsRes> result = commentService.getComments(commentIdx);
            return new RegularResponse<>(result);
        }catch (Exception e){
            e.printStackTrace();
            return new RegularResponse<>(new ArrayList<>());
        }
    }

    @PostMapping("/getReplies")
    public RegularResponse<List<GetReplyRes>> getReplies(@RequestBody GetReplyReq getReplyReq){
        try {
            List<GetReplyRes> result = commentService.getReplies(getReplyReq);
            return new RegularResponse<>(result);
        }catch (Exception e){
            e.printStackTrace();
            return new RegularResponse<>(new ArrayList<>());
        }
    }


}
