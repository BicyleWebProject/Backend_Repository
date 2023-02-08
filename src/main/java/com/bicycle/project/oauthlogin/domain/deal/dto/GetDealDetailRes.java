package com.bicycle.project.oauthlogin.domain.deal.dto;


import com.bicycle.project.oauthlogin.domain.BasicDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetDealDetailRes extends BasicDto {

    private String title;
    private String userNickname;
    private String imageUrl1;
    private String imageUrl2;
    private String imageUrl3;

    private String content;
    private Long price;
    private String userImageUrl;
    private Long userId;
    private Long total;

    private String location;

}
