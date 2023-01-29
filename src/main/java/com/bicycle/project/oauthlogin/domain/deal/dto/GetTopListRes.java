package com.bicycle.project.oauthlogin.domain.deal.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetTopListRes {

    private String title;
    private Long total;

    private Long dealId;

    private Long categoryId;


}
