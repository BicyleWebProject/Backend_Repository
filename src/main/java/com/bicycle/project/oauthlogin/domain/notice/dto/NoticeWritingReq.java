package com.bicycle.project.oauthlogin.domain.notice.dto;


import com.bicycle.project.oauthlogin.domain.BasicDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeWritingReq extends BasicDto {

    @Lob
    private String content;

    private String title;

    private Long categoryId;

    private Long userId;

}
