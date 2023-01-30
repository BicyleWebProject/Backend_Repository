package com.bicycle.project.oauthlogin.controller.auth.dto;

import com.bicycle.project.oauthlogin.data.entity.User;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {


    private String userEmail;

    private String password;

    public User toUser(PasswordEncoder passwordEncoder){
        return User.builder()
                .userEmail(userEmail)
                .password(passwordEncoder.encode(password))
                .build();
    }
}
