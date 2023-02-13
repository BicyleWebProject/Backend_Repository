package com.bicycle.project.oauthlogin.domain.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NoticeList {

    private String title;
    private String content;
    private String updatedAt;

    private Long noticeBoardId;
}
