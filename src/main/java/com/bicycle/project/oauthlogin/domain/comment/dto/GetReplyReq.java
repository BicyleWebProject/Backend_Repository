package com.bicycle.project.oauthlogin.domain.comment.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetReplyReq {

    private Long communityId;
    private Long commentId;
}
