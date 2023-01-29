package com.bicycle.project.oauthlogin.data.entity;

import javax.persistence.*;

@Entity
public class CommunityLike extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long CommunityLikeId;

    @ManyToOne
    @JoinColumn(name="COMMUNITY_ID")
    private Community community;

    @ManyToOne
    @JoinColumn(name="USER_ID")
    private User user;
}
