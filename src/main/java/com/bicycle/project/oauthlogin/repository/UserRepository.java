package com.bicycle.project.oauthlogin.repository;

import com.bicycle.project.oauthlogin.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserEmail(String userEmail);


    Optional<User> findByUserIdx(String token);

    Optional<User> findUserIdxByUsername(String username);


    //@EntityGraph(attributePaths = "authorities")
    //Optional<User> findOneWithAuthoritiesByUserEmail(String userEmail);

    //User findByUserIdx(Long userIdx);

    @Transactional
    void deleteByUserEmail(String userEmail);

    Optional<User> findByUserIdx(Long userIdx);

    List<User> findByUsername(String username);

    Optional<User> findByUserEmailAndProvider(String email, String provider);

}
