package com.bicycle.project.oauthlogin.config.security;

import com.bicycle.project.oauthlogin.config.RegularException;
import com.bicycle.project.oauthlogin.config.RegularResponseStatus;
import com.bicycle.project.oauthlogin.controller.auth.dto.TokenDto;
import com.bicycle.project.oauthlogin.data.entity.User;
import com.bicycle.project.oauthlogin.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.Base64UrlCodec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class TokenProvider {

    private final Logger LOGGER = LoggerFactory.getLogger(TokenProvider.class);
    private final UserDetailsService userDetailsService;

    private final UserRepository userRepository;

    private final Long accessTokenValidMillisecond = 60 * 60 * 1000L; // 1 hour
    private final Long refreshTokenValidMillisecond = 14 * 24 * 60 * 60 * 1000L; // 14 day
    private String ROLES = "roles";

    @Value("${springboot.jwt.secret}")
    private String secretKey = "secretKey";

    @PostConstruct
    protected void init(){
        secretKey = Base64UrlCodec.BASE64URL.encode(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    //Long userPk
    public TokenDto createTokenDto(Long userIdx, List<String> roles){

        Claims claims = Jwts.claims().setSubject(String.valueOf(userIdx));
        claims.put(ROLES, roles);

        Date now = new Date();


        String accessToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenValidMillisecond))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        String refreshToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setExpiration(new Date(now.getTime() + refreshTokenValidMillisecond))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return TokenDto.builder()
                .userIdx(userIdx)
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpireDate(accessTokenValidMillisecond)
                .build();
    }
 /*
 인증이 성공하면 SecurityContextHolder에 저장할 Authentication 객체를 생성한다.
  */
    public Authentication getAuthentication(String accessToken)  {

        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(accessToken));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserPk(String token){
        //말그래도 userPK, userIdx값을 가져온다.
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }


    private Claims parseClaims(String token){
        try{
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        }catch (ExpiredJwtException e){
            return e.getClaims();
        }
    }

    public String resolveToken(HttpServletRequest request){
        return request.getHeader("X-AUTH-TOKEN");
    }

    public boolean validationToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
//            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
//            return true;
            return !claims.getBody().getExpiration().before(new Date());
        } catch (SecurityException | MalformedJwtException e) {
            log.error("잘못된 Jwt 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.error("만료된 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("지원하지 않는 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.error("잘못된 토큰입니다.");
        }
        return false;
    }
    /*
    Access Token뿐만 아닌, RefreshToken 검증하기 위한 코드들
     */
    @Transactional
    public User getUserByRefreshToken(String token) throws RegularException {
        return userRepository.findByUserIdx(token)
                .orElseThrow(() -> new RegularException(RegularResponseStatus.REQUEST_ERROR));
    }

//    @Transactional
//    public void setRefreshToken(String userEmail, String refreshToken){
//        userRepository.findByUserEmail(userEmail).ifPresent(User -> User.setRefreshToken(refreshToken));
//    }
//
//    @Transactional
//    public void removeRefreshToken(String token){
//        userRepository.findByRefreshToken(token)
//                .ifPresent()
//    }
}