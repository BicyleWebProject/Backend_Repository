package com.bicycle.project.oauthlogin.domain.community.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TopListDto {

    private String title;

    private Long total;

    private Integer communityId;

    private Integer categoryId;
}
