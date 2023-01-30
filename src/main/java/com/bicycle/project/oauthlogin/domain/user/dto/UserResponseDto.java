package com.bicycle.project.oauthlogin.domain.user.dto;

import com.bicycle.project.oauthlogin.data.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
public class UserResponseDto {

    private final Long userIdx;
    private final String userEmail;
    private final String username;
    private final List<String> roles;
    private Collection<? extends GrantedAuthority> authorities;
    private final LocalDateTime modifiedDate;

    public UserResponseDto(User user){
        this.userIdx = user.getUserIdx();
        this.userEmail = user.getUserEmail();
        this.username = user.getUsername();
        this.roles = user.getRoles();
        this.modifiedDate = user.getUpdatedAt();

    }
}
