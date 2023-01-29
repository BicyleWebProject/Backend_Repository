package com.bicycle.project.oauthlogin.controller.auth.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    //사이즈 변경 가능
    @NotNull
    @Size(min=50, max=100)
    private String userEmail;

    @NotNull
    @Size(min=50, max=100)
    private String password;
}
