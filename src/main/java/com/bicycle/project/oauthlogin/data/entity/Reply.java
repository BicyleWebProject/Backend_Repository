package com.bicycle.project.oauthlogin.data.entity;

import com.bicycle.project.oauthlogin.data.entity.BaseEntity;
import com.bicycle.project.oauthlogin.data.entity.Community;
import com.bicycle.project.oauthlogin.data.entity.User;

import javax.persistence.*;

@Entity
public class Reply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REPLY_ID")
    private Long replyId;

    @ManyToOne
    @JoinColumn(name = "COMMENT_ID")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "COMMUNITY_ID")
    private Community community;

    @ManyToOne
    @JoinColumn(name="USER_ID")
    private User user;

    @Column
    @Lob
    private String content;

}
