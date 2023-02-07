package com.bicycle.project.oauthlogin.domain.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommunityListReq {

    @NotNull
    private int page;
}
