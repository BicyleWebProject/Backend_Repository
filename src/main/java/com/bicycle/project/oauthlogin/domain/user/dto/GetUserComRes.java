package com.bicycle.project.oauthlogin.domain.user.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserComRes {

    private String userEmail;
    private String noteCategory;
    private String userNickname;
    private String userImageUrl;
    private String title;
    private String updatedAt;
    private int total;
}
