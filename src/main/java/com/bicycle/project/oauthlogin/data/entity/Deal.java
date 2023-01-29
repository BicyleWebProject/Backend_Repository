package com.bicycle.project.oauthlogin.data.entity;

import javax.persistence.*;

@Entity
public class Deal extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEAL_ID")
    private Long dealId;

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

    @Column
    private int price;

    @Column
    @Lob
    private String imageUrl1;

    @Column
    @Lob
    private String imageUrl2;

    @Column
    @Lob
    private String imageUrl3;
}
