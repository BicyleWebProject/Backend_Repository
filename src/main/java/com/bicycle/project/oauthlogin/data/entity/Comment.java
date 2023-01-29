package com.bicycle.project.oauthlogin.data.entity;

import javax.persistence.*;

@Entity
public class Comment extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId; //Long이 나을것 같긴한데 나중에..

    @ManyToOne
    @JoinColumn(name="COMMUNITY_ID")
    private Community community;

    @ManyToOne
    @JoinColumn(name="CATEGORY_ID")
    private Category category;

    @Column
    @Lob
    private String text;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;


}
