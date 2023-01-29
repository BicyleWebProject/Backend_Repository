package com.bicycle.project.oauthlogin.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nimbusds.oauth2.sdk.token.RefreshToken;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER_REFRESH_TOKEN")
public class UserRefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(nullable = false)
    private Long key;

    @Column(nullable = false)
    private String token;

    public UserRefreshToken updateToken(String token){
        this.token = token;
        return this;
    }

    @Builder
    public UserRefreshToken(Long key, String token) {
        this.key = key;
        this.token = token;
    }
}
