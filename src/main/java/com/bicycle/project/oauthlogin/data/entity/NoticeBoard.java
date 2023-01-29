package com.bicycle.project.oauthlogin.data.entity;

import javax.persistence.*;

@Entity
public class NoticeBoard extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="NOTICE_BOARD_ID")
    private Long noticeBoardId;

    @ManyToOne
    @JoinColumn(name="USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name="CATEGORY_ID")
    private Category category;

    @Column
    private String title;

    @Column
    @Lob
    private String content;

}
