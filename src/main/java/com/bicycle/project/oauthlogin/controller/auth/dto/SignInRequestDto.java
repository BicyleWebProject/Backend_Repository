package com.bicycle.project.oauthlogin.controller.auth.dto;

import com.bicycle.project.oauthlogin.data.entity.User;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class SignInRequestDto {
//UserLoginRequestDto 와 같음
    private String userEmail;

    private String password;


    public User toUser(PasswordEncoder passwordEncoder){
        return User.builder()
                .userEmail(userEmail)
                .password(passwordEncoder.encode(password))
                .build();
    }
}
