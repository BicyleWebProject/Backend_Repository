package com.bicycle.project.oauthlogin.domain.community.dto;



import com.bicycle.project.oauthlogin.domain.BasicDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunityLikeReq extends BasicDto {


    private Long communityId;

    private Long userId;

}
