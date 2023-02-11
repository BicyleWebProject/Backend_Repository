package com.bicycle.project.oauthlogin.domain.user.dto;

import com.bicycle.project.oauthlogin.data.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUser {

    private String userId;

    private String newUsername;

    private String location;

    private String interestedAt;

    @Lob
    private String userImgUrl;

    public User toEntity(){
        return User.builder()
                .userEmail(userId)
                .username(newUsername)
                .location(location)
                .interestedAt(interestedAt)
                .profileImageUrl(userImgUrl)
                .build();
    }
}
