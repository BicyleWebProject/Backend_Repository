package com.bicycle.project.oauthlogin.domain.user.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserComReq {

    private Long userId;
    private String userEmail;
    private Integer noteCategory;

    @NotNull
    private Integer page;
}
