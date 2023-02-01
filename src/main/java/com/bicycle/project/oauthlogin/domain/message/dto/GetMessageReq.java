package com.bicycle.project.oauthlogin.domain.message.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetMessageReq {

    private Long receiverId;

    private Long senderId;

}
