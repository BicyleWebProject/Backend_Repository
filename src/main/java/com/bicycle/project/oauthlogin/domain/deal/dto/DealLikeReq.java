package com.bicycle.project.oauthlogin.domain.deal.dto;



import com.bicycle.project.oauthlogin.domain.BasicDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DealLikeReq extends BasicDto {

    private Long userId;

    private Long dealId;




}
