package com.bicycle.project.oauthlogin.repository;

import com.bicycle.project.oauthlogin.data.entity.auth.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
