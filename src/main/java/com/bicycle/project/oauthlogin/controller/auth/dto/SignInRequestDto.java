package com.bicycle.project.oauthlogin.controller.auth.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
public class SignInRequestDto {

    private String userEmail;

    private String password;

}
