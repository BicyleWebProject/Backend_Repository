package com.bicycle.project.oauthlogin.domain.message.dto;


import com.bicycle.project.oauthlogin.domain.BasicDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WriteMessageReq extends BasicDto {

    private Long senderId;

    private Long receiverId;

    private String messageContent;

}
