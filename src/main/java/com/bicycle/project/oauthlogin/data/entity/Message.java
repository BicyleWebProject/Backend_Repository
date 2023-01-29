//package com.bicycle.project.oauthlogin.data.entity;
//
//import javax.persistence.*;
//
//@Entity
//public class Message extends BaseEntity{
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "MESSAGE_ID")
//    private int messageId;
//
//    @OneToOne
//    @JoinColumn(name="USER_ID")
//    private User receiverId;
//
//    @OneToOne
//    @JoinColumn(name="USER_ID")
//    private User senderId;
//
//    @Column
//    private String messageContent;
//
//    @Column
//    private String isChecked;
//
//
//}
