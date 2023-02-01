package com.bicycle.project.oauthlogin.domain.message.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetMessageRes {

    private Long receiverId;

    private Long senderId;

    private String createdAt;

    private String isChecked;

    private String messageContent;

    private Long messageId;

}
