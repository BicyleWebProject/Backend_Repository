package com.bicycle.project.oauthlogin.domain.community.dto;

//import com.example.backend.domain.BasicDto;
import com.bicycle.project.oauthlogin.domain.BasicDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCommunityReq extends BasicDto {

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;



    private Long categoryId;

    private Long communityId;
}
