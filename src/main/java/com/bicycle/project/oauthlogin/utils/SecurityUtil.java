package com.bicycle.project.oauthlogin.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class SecurityUtil {

    private static final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

    public SecurityUtil() {
    }

    public static Optional<String> getCurrentUserEmail(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication==null){
            logger.debug("SecurityContext에 인증정보가 없음.");
            return Optional.empty();
        }

        String userEmail = null;
        //이게 기본 구현 메서드 & 읽기 전용이라 USername을 읽어와야되는데, 우리가 필요한 userEmail에선 어떻게 적용될지 모르겠어용..
        if(authentication.getPrincipal() instanceof UserDetails){
            UserDetails securityUser = (UserDetails) authentication.getPrincipal();
            userEmail = securityUser.getUsername();
        }else if(authentication.getPrincipal() instanceof String){
            userEmail = (String) authentication.getPrincipal();
        }

        return Optional.ofNullable(userEmail);
    }
}
