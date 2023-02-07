package com.bicycle.project.oauthlogin.config.security;

//import com.bicycle.project.oauthlogin.oauth.service.CustomOAuth2UserService;
//import com.bicycle.project.oauthlogin.oauth.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig  {


    private final TokenProvider tokenProvider;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;


    @Autowired
    public SecurityConfig(TokenProvider jwtTokenProvider, CustomAccessDeniedHandler customAccessDeniedHandler, CustomAuthenticationEntryPoint customAuthenticationEntryPoint){
        this.tokenProvider = jwtTokenProvider;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .httpBasic().disable()
                .csrf().disable()

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/sign-api/sign-up", "/sign-api/sign-in",
                        "/sign-api/reissue", "/v1/social/**","/community/**", "/notice/**","/deal/**","/user**","/login/**","/comment/**","/message/**").permitAll()
                .antMatchers(HttpMethod.GET, "/oauth/kakao/**", "/community/**", "/notice/**","/deal/**","/user**","/login/**","/comment/**","/message/**").permitAll()
                .antMatchers(HttpMethod.GET, "/exception/**").permitAll()

                .antMatchers(HttpMethod.GET, "/user/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/user/**").permitAll()
                .antMatchers(HttpMethod.GET, "/community/**").permitAll()
                .antMatchers(HttpMethod.POST, "/community/**").permitAll()
                .antMatchers(HttpMethod.GET, "/deal/**").permitAll()

                .antMatchers(HttpMethod.DELETE,"/community/**", "/notice/**","/deal/**","/user**","/login/**","/comment/**" ).permitAll()
                .antMatchers(HttpMethod.PATCH,"/community/**", "/notice/**","/deal/**","/user**","/login/**","/comment/**","/message/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/user/user/**").permitAll()
                .anyRequest().hasRole("USER")
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .accessDeniedHandler(new AccessDeniedHandler() {
                    @Override
                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                        //권한 문제 생겼을 시 호출
                        response.setStatus(403);
                        response.setCharacterEncoding("utf-8");
                        response.setContentType("login/html; charset=UTF-8"); //서비스 단에 맞게 변경하기
                        response.getWriter().write("권한 없는 사용자입니다.");
                    }
                })
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
                    @Override
                    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                        //인증문제 생겼을 시 호출함
                        response.setStatus(403);
                        response.setContentType("login/html; charset=UTF-8");
                        response.getWriter().write("인증되지 않은 사용자입니다.");
                    }
                });
                //jwt 토큰 필터를 id/pwd 인증 필터 전에 넣기
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
}