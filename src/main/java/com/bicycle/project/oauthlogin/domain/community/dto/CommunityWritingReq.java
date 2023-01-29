package com.bicycle.project.oauthlogin.domain.community.dto;



import com.bicycle.project.oauthlogin.domain.BasicDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommunityWritingReq extends BasicDto {

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    @NotEmpty
    private Long userId;

    @Lob
    private String communityImageUrl;


    private Long categoryId;



}
