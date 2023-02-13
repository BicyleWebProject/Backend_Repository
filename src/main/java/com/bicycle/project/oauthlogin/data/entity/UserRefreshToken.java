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
    private Long refreshTokenId;

    @Column(nullable = false, unique = true) //userIdx 저장
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

}
