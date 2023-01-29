package com.bicycle.project.oauthlogin.repository;

import com.bicycle.project.oauthlogin.data.entity.UserRefreshToken;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenJpaRepo extends JpaRepository<UserRefreshToken, String> {

    Optional<UserRefreshToken> findByKey(Long key);
}
