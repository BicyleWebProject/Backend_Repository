package com.bicycle.project.oauthlogin.controller.auth.dto;

import com.bicycle.project.oauthlogin.data.entity.User;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SignUpRequestDto {

    private String userEmail;

    private String password;

    private String username;
    private String provider;

    public User toEntity(PasswordEncoder passwordEncoder){
        return User.builder()
                .userEmail(userEmail)
                .password(passwordEncoder.encode(password))
                .username(username)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();

    }

    public User toEntity(){
        return User.builder()
                .userEmail(userEmail)
                .username(username)
                .provider(provider)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
    }
}
