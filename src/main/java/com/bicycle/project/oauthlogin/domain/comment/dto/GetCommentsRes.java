package com.bicycle.project.oauthlogin.domain.comment.dto;


import com.bicycle.project.oauthlogin.domain.BasicDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCommentsRes extends BasicDto {


    private int communityId;
    private String userNickname;
    private long userId;
    private String content;

    private String userImageUrl;
    private int commentId;
}
