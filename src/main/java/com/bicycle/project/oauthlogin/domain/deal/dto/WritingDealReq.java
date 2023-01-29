package com.bicycle.project.oauthlogin.domain.deal.dto;


import com.bicycle.project.oauthlogin.domain.BasicDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WritingDealReq extends BasicDto {


    private int dealId;


    private Long userId;


    private int categoryId;


    private String title;


    private String content;


    private int price;

    private String imageUrl1;

    private String imageUrl2;

    private String imageUrl3;

}
