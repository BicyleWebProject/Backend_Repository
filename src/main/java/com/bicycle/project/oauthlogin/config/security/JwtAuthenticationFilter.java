package com.bicycle.project.oauthlogin.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
인증 or 권한이 필요한 요청 -> Header 확인해 토큰 유무 확인 -> header 유효하면 토큰에서 회원 꺼내서 조회 ->
회원객체 + 회원권한으로 authentication 만들고 SecurityContextHolder에 들어가 Authentication을 SecurityContext에 넣어줌
검증 서옥ㅇ -> Access, Refresh Token 발급
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider jwtProvider;

    public JwtAuthenticationFilter(TokenProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = jwtProvider.resolveToken((HttpServletRequest) request);
        log.info("veryfing token!");
        if(token != null && jwtProvider.validationToken(token)){
            Authentication authentication = jwtProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
