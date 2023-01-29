package com.bicycle.project.oauthlogin.domain.notice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NoticeListReq {

    private String contains;


    @NotNull
    private int page;
}
