package com.bicycle.project.oauthlogin.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final TokenProvider jwtProvider;

    public JwtAuthenticationFilter(TokenProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = jwtProvider.resolveToken((HttpServletRequest) request);

        log.info("veryfing token!");
        log.info(((HttpServletRequest) request).getRequestURL().toString());

        if(token != null && jwtProvider.validationToken(token)){
            Authentication authentication = null;
            try {
                authentication = jwtProvider.getAuthentication(token);
            } catch (CustomAuthenticationEntryPoint e) {
                throw new RuntimeException(e);
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }
        chain.doFilter(request, response);
    }
}
