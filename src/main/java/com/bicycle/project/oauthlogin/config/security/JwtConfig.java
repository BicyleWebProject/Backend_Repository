//package com.bicycle.project.oauthlogin.oauth.config.security;
//
//import com.bicycle.project.oauthlogin.oauth.token.AuthTokenProvider;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class JwtConfig {
//    @Value("${jwt.secret}")
//    private String secret;
//
//    @Bean
//    public AuthTokenProvider jwtProvider() {
//        return new AuthTokenProvider(secret);
//    }
//}
