package com.bicycle.project.oauthlogin.config.security;

//import com.bicycle.project.oauthlogin.oauth.service.CustomOAuth2UserService;
//import com.bicycle.project.oauthlogin.oauth.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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

//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.httpBasic().disable() // REST API는 UI를 사용하지 않으므로 기본설정을 비활성화
//
//                .csrf().disable() // REST API는 csrf 보안이 필요 없으므로 비활성화
//
//                .sessionManagement()
//                .sessionCreationPolicy(
//                        SessionCreationPolicy.STATELESS) // JWT Token 인증방식으로 세션은 필요 없으므로 비활성화
//
//                .and()
//                .authorizeRequests() // 리퀘스트에 대한 사용권한 체크
//                .antMatchers("/sign-api/sign-in", "/sign-api/sign-up",
//                        "/sign-api/exception").permitAll() // 가입 및 로그인 주소는 허용
//                .antMatchers(HttpMethod.GET, "/product/**").permitAll() // product로 시작하는 Get 요청은 허용
//
//                .antMatchers("**exception**").permitAll()
//
//                .anyRequest().hasRole("ADMIN") // 나머지 요청은 인증된 ADMIN만 접근 가능
//
//                .and()
//                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
//                .and()
//                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
//
//                .and()
//                .addFilterBefore(new JwtFilter(tokenProvider),
//                        UsernamePasswordAuthenticationFilter.class); // JWT Token 필터를 id/password 인증 필터 이전에 추가
//    }
//
//
//    @Override
//    public void configure(WebSecurity webSecurity) {
//        webSecurity.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**",
//                "/swagger-ui.html", "/webjars/**", "/swagger/**", "/sign-api/exception");
//    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//        http.csrf().disable();
//        http.authorizeRequests()
//                //.antMatchers("/sign-api/sign-in", "/sign-api/sign-up","/sign-api/exception") //이부분은 권한 설정. 나중에 바꿔줄 수 있음
//                .anyRequest().permitAll();
//
//        return http.build();
//
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//        http.cors().and().csrf().disable();
//        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.POST,"/sign-in", "/sign-up",
//                        "/sign-api/sign-in", "/sign-api/sign-up").permitAll()
//                .anyRequest().hasRole("USER");
//        return http.build();
        http
                .httpBasic().disable()
                .csrf().disable()

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/sign-api/sign-up", "/sign-api/sign-in",
                        "/sign-api/reissue", "/v1/social/**","/community/**", "/notice/**","/deal/**","/user**","login/**").permitAll()
                .antMatchers(HttpMethod.GET, "/oauth/kakao/**", "/community/**", "/notice/**","/deal/**","/user**","login/**").permitAll()
                .antMatchers(HttpMethod.GET, "/exception/**").permitAll()
                .antMatchers(HttpMethod.DELETE,"/community/**", "/notice/**","/deal/**","/user**","login/**" ).permitAll()
                .antMatchers(HttpMethod.PATCH,"/community/**", "/notice/**","/deal/**","/user**","login/**").permitAll()
                .anyRequest().hasRole("USER");
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