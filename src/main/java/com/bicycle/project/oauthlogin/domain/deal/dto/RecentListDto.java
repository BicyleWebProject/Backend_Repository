package com.bicycle.project.oauthlogin.domain.deal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecentListDto {

    /*
    최근 거래 게시글 조회하는 dto
     */
    private String userEmail; //거래게시글 작성한 user의 Id
    private String title;
    private Long dealId;
    private Long categoryId;
    private String imageUrl1;
    private String imageUrl2;
    private String imageUrl3;
    private Integer price;
}
