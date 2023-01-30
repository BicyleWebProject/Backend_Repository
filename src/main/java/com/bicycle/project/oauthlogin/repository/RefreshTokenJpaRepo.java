package com.bicycle.project.oauthlogin.repository;

import com.bicycle.project.oauthlogin.data.entity.UserRefreshToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenJpaRepo extends JpaRepository<UserRefreshToken, String> {

    Optional<UserRefreshToken> findByUserKey(Long userKey);
}
