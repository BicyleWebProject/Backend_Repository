package com.bicycle.project.oauthlogin.domain.deal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecentListReq {

    private Long categoryId;

    @NotNull
    private int page;
}
