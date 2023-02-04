package com.bicycle.project.oauthlogin.config.security;

import com.bicycle.project.oauthlogin.controller.auth.dto.TokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.Base64UrlCodec;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class TokenProvider {

    private final Logger LOGGER = LoggerFactory.getLogger(TokenProvider.class);
    private final UserDetailsService userDetailsService;

    //private final Key key;
//    private final long tokenValidMillisecond = 1000L * 60 * 60;
    private final Long accessTokenValidMillisecond = 60 * 60 * 1000L; // 1 hour
    private final Long refreshTokenValidMillisecond = 14 * 24 * 60 * 60 * 1000L; // 14 day
    private String ROLES = "roles";

    @Value("${springboot.jwt.secret}")
    private String secretKey = "secretKey";

//    public TokenProvider(@Value("${springboot.jwt.secret}") String secretKey){
//        byte[] KeyBytes = Decoders.BASE64URL.decode(secretKey);
//        this.key = Keys.hmacShaKeyFor(KeyBytes);
//    }

    @PostConstruct
    protected void init(){
        secretKey = Base64UrlCodec.BASE64URL.encode(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    //Long userPk
    public TokenDto createTokenDto(Long userIdx, List<String> roles){

        Claims claims = Jwts.claims().setSubject(String.valueOf(userIdx));
        claims.put(ROLES, roles);

//        long now = (new Date()).getTime();
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
    public Authentication getAuthentication(String accessToken) throws CustomAuthenticationEntryPoint {

        Claims claims = parseClaims(accessToken);

        if(claims.get(ROLES) ==  null){
            throw new CustomAuthenticationEntryPoint();
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
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
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
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

//
//
//
//
//    @PostConstruct
//    protected void init() {
//        LOGGER.info("[init] JwtTokenProvider 내 secretKey 초기화 시작");
//        System.out.println(secretKey);
//        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
//        System.out.println(secretKey);
//        LOGGER.info("[init] JwtTokenProvider 내 secretKey 초기화 완료");
//        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
//    }
//
//
//    public String createToken(String userEmail, List<String> roles) {
//        LOGGER.info("[createToken] 토큰 생성 시작");
//        Claims claims = Jwts.claims().setSubject(userEmail);
//        claims.put("roles", roles);
//
//        Date now = new Date();
//        String token = Jwts.builder()
//                .setClaims(claims)
//                .setIssuedAt(now)
//                .setExpiration(new Date(now.getTime() + tokenValidMillisecond))
//                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret 값 세팅
//                .compact();
//
//        LOGGER.info("[createToken] 토큰 생성 완료");
//        return token;
//    }
//
//
//    public Authentication getAuthentication(String token) {
//        LOGGER.info("[getAuthentication] 토큰 인증 정보 조회 시작");
//        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUsername(token));
//        LOGGER.info("[getAuthentication] 토큰 인증 정보 조회 완료, UserDetails UserName : {}",
//                userDetails.getUsername());
//        return new UsernamePasswordAuthenticationToken(userDetails, "",
//                userDetails.getAuthorities());
//    }
//
//
//
//
//    public String getUsername(String token) {
//        LOGGER.info("[getUsername] 토큰 기반 회원 구별 정보 추출");
//        String info = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody()
//                .getSubject();
//        LOGGER.info("[getUsername] 토큰 기반 회원 구별 정보 추출 완료, info : {}", info);
//        return info;
//    }
//
//
//    public String resolveToken(HttpServletRequest request) {
//        LOGGER.info("[resolveToken] HTTP 헤더에서 Token 값 추출");
//        return request.getHeader("X-AUTH-TOKEN");
//    }
//
//
//    public boolean validateToken(String token) {
//        LOGGER.info("[validateToken] 토큰 유효 체크 시작");
//        try {
//            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
//            LOGGER.info("[validateToken] 토큰 유효 체크 완료");
//            return !claims.getBody().getExpiration().before(new Date());
//        } catch (Exception e) {
//            LOGGER.info("[validateToken] 토큰 유효 체크 예외 발생");
//            return false;
//        }
//    }
}