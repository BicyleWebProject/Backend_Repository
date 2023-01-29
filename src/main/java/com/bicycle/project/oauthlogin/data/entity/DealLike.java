package com.bicycle.project.oauthlogin.data.entity;

import javax.persistence.*;

@Entity
public class DealLike extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEAL_LIKE_ID")
    private Long dealLikeId;

    @ManyToOne
    @JoinColumn(name="DEAL_ID")
    private Deal deal;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
}
