package com.bicycle.project.oauthlogin.domain.user.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetOtherRes {


    private String username;

    private String email;

    private String profileImageUrl;

}
