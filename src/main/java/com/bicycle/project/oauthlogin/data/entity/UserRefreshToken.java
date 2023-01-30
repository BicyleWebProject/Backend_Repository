package com.bicycle.project.oauthlogin.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nimbusds.oauth2.sdk.token.RefreshToken;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "refresh_token")
public class UserRefreshToken extends  BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refresh_token_id;
//    private String id;


    @Column(nullable = false) //userIdx 저장
    private Long userKey;

    @Column(nullable = false)
    private String token;

    public UserRefreshToken updateToken(String token){
        this.token = token;
        return this;
    }

    @Builder
    public UserRefreshToken(Long key, String token) {
        this.userKey = key;
        this.token = token;
    }

//    @Override
//    public String getStatus() {
//        return super.getStatus();
//    }
//
//    @Override
//    public LocalDateTime getCreatedAt() {
//        return super.getCreatedAt();
//    }
//
//    @Override
//    public LocalDateTime getUpdatedAt() {
//        return super.getUpdatedAt();
//    }
}
