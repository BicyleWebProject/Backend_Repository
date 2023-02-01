package com.bicycle.project.oauthlogin.domain.notice;


import com.bicycle.project.oauthlogin.config.RegularException;
import com.bicycle.project.oauthlogin.config.RegularResponse;
import com.bicycle.project.oauthlogin.domain.notice.dto.NoticeList;
import com.bicycle.project.oauthlogin.domain.notice.dto.NoticeListReq;
import com.bicycle.project.oauthlogin.domain.notice.dto.NoticeWritingReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private NoticeDao noticeDao;




    @PostMapping ("/getBoard")
    public RegularResponse<List<NoticeList>> getBoard(@RequestBody @Valid NoticeListReq noticeListReq){
        try {
            if (noticeListReq.getContains() == null)
                noticeListReq.setContains("");
            List<NoticeList> getBoard = noticeService.getBoard(noticeListReq);
            Integer getSumBoard = noticeService.getSumBoard(noticeListReq);
            return new RegularResponse<>(getBoard,getSumBoard);
        }catch (Exception e){
            e.printStackTrace();
            return new RegularResponse<>(new ArrayList<NoticeList>());
        }

    }

    @GetMapping("/getBoard/{boardId}")
    public RegularResponse<NoticeList> getBoardDetail(@PathVariable @Valid Long boardId){
    try {
        NoticeList result = noticeService.getBoardDetail(boardId);
        return new RegularResponse<>(result);
    }catch (RegularException regularException){
        regularException.printStackTrace();
        return new RegularResponse<>(regularException.getStatus());
    }
    }

    @PostMapping("/writeNotice")
    public RegularResponse<String> writeNotice(@RequestBody @Valid NoticeWritingReq noticeWritingReq){
        try {
            String result = noticeService.writeNotice(noticeWritingReq);
            return new RegularResponse<>(result);
        }catch (RegularException regularException){
            regularException.printStackTrace();
            return new RegularResponse<>(regularException.getStatus());
        }
    }
}
