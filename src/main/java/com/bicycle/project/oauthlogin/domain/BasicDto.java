package com.bicycle.project.oauthlogin.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.TimeZone;

@MappedSuperclass
@Getter
@EntityListeners(AuditingEntityListener.class)
@Setter
public abstract class BasicDto {

    @ColumnDefault(value = "Y")
    private String status = "Y";


    @Column
    private LocalDateTime createdAt;


    @Column
    private LocalDateTime updatedAt;

    public BasicDto(){
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        Locale.setDefault(Locale.KOREA);
        this.status = "Y";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

    }
}
