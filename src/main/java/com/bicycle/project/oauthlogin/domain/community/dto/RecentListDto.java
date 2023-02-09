package com.bicycle.project.oauthlogin.domain.community.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecentListDto {

    private String userEmail; //사용한 user의 Id
    private Integer categoryId;
    private String title;
    private String content;
    private String communityImageUrl;
    private Integer communityId;
}
