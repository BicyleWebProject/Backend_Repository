package com.bicycle.project.oauthlogin.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegularException extends Exception{

    private RegularResponseStatus status;
}
