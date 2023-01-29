package com.bicycle.project.oauthlogin.domain.deal.dto;



import com.bicycle.project.oauthlogin.domain.BasicDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDealReq extends BasicDto {

    private String content;

    private Long price;

    private String title;

    private Long categoryId;

    private Long dealId;

}
