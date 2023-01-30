package com.bicycle.project.oauthlogin.repository;

import com.bicycle.project.oauthlogin.data.entity.UserRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, String> {
//    UserRefreshToken findByUserId(Long userIdx);
//    UserRefreshToken findByUserIdAndRefreshToken(Long userIdx, String refreshToken);

    Optional<UserRefreshToken> findByUserKey(Long Key);
}
