package com.bicycle.project.oauthlogin.controller.auth.dto;

import com.bicycle.project.oauthlogin.common.Role;
import com.bicycle.project.oauthlogin.data.entity.User;
import com.bicycle.project.oauthlogin.domain.BasicDto;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collections;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SignUpRequestDto extends BasicDto {

    private String userEmail;

    private String password;

    private String username;

    private String interestedAt;

    private String location;

    private Role roles;

    public User toEntity(PasswordEncoder passwordEncoder){
        return User.builder()
                .userEmail(userEmail)
                .password(passwordEncoder.encode(password))
                .username(username)
                .interestedAt(interestedAt)
                .location(location)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
    }

    public User toEntity(){
        return User.builder()
                .userEmail(userEmail)
                .username(username)
                .roles(Collections.singletonList("ROLE_USER"))
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
