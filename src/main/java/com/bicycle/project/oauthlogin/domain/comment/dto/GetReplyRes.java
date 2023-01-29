package com.bicycle.project.oauthlogin.domain.comment.dto;


import com.bicycle.project.oauthlogin.domain.BasicDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetReplyRes extends BasicDto {

    private Long communityId;
    private Long commentId;
    private Long userId;
    private String content;
    private String userImageUrl;
    private Long replyId;

}
