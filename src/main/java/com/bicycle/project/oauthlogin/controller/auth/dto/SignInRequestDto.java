package com.bicycle.project.oauthlogin.controller.auth.dto;

import com.bicycle.project.oauthlogin.data.entity.User;
import com.bicycle.project.oauthlogin.domain.BasicDto;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class SignInRequestDto extends BasicDto {
//UserLoginRequestDto 와 같음
    private String userEmail;

    private String password;


    public User toUser(PasswordEncoder passwordEncoder){
        return User.builder()
                .userEmail(userEmail)
                .password(passwordEncoder.encode(password))
                .build();
    }

    @Override
    public String getStatus() {
        return super.getStatus();
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return super.getCreatedAt();
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return super.getUpdatedAt();
    }
}
