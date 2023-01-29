package com.bicycle.project.oauthlogin.data.entity;

import javax.persistence.*;

@Entity
public class Community extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMUNITY_ID")
    private Long communityId;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name="CATEGORY_ID")
    private Category category;

    @Column
    private String title;

    @Column
    @Lob
    private String content;

    @Column
    @Lob
    private String communityImageUrl;
}
