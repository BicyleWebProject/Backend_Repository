package com.bicycle.project.oauthlogin.domain.community.dto;


import com.bicycle.project.oauthlogin.domain.BasicDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunityDetailRes extends BasicDto {

    private String title;
    private String userNickname;
    private String communityImageUrl;
    private String content;
    private String userImageUrl;

    private Long userId;

    private Long total;

}
