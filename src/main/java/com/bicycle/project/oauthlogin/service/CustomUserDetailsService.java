package com.bicycle.project.oauthlogin.service;


import com.bicycle.project.oauthlogin.data.entity.User;
import com.bicycle.project.oauthlogin.exception.CUserNotFoundException;
import com.bicycle.project.oauthlogin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.attribute.UserPrincipal;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

//    private final UserJpaRepo userJpaRepo;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String userIdx) throws UsernameNotFoundException{
        return userRepository.findByUserIdx(Long.parseLong(userIdx)).orElseThrow(CUserNotFoundException::new );
    }
}
