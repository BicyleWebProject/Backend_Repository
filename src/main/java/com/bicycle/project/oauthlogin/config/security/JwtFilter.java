package com.bicycle.project.oauthlogin.config.security;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

public class JwtFilter extends GenericFilterBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtFilter.class);
    private TokenProvider tokenProvider;
    public JwtFilter(TokenProvider jwtTokenProvider){
        this.tokenProvider = jwtTokenProvider;
    }

    //    @Override
//    protected void doFilterInternal(HttpServletRequest servletRequest,
//                                    HttpServletResponse servletResponse,
//                                    FilterChain filterChain) throws ServletException, IOException{
//        String token = jwtTokenProvider.resolveToken(servletRequest);
//    }
//    @Override
//    protected void doFilterInternal(HttpServletRequest servletRequest,
//                                    HttpServletResponse servletResponse,
//                                    FilterChain filterChain) throws ServletException, IOException {
//        String token = tokenProvider.resolveToken(servletRequest);
//        LOGGER.info("[doFilterInternal] token 값 추출 완료. token : {}", token);
//
//        LOGGER.info("[doFilterInternal] token 값 유효성 체크 시작");
//        if (token != null && tokenProvider.validateToken(token)) {
//            Authentication authentication = tokenProvider.getAuthentication(token);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            LOGGER.info("[doFilterInternal] token 값 유효성 체크 완료");
//        }
//
//        filterChain.doFilter(servletRequest, servletResponse);
//    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException{
        String token = tokenProvider.resolveToken((HttpServletRequest) request);

        if(token != null && tokenProvider.validationToken(token)){
            Authentication authentication = null;
            try {
                authentication = tokenProvider.getAuthentication(token);
            } catch (CustomAuthenticationEntryPoint e) {
                throw new RuntimeException(e);
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}