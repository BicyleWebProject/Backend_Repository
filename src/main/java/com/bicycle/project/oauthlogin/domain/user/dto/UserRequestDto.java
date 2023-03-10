package com.bicycle.project.oauthlogin.domain.user.dto;

import com.bicycle.project.oauthlogin.data.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    private String userEmail;
    private String username;

    private String location;

    private String interestedAt;

    private String userImgUrl;

    public User toEntity(){
        return User.builder()
                .userEmail(userEmail)
                .username(username)
                .location(location)
                .interestedAt(interestedAt)
//                .profileImageUrl(userImgUrl)
                .build();
    }


}
