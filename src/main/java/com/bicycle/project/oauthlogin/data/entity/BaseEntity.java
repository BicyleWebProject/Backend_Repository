package com.bicycle.project.oauthlogin.data.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    private String status;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @CreatedDate
    @Column(updatable = true)
    private LocalDateTime updatedAt;
}
