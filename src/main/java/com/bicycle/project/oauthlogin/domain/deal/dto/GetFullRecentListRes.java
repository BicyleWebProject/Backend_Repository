package com.bicycle.project.oauthlogin.domain.deal.dto;

import com.bicycle.project.oauthlogin.domain.BasicDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetFullRecentListRes extends BasicDto {

    /*
    카테고리별 최근 게시글 조회!
     */
    @Lob
    private String content;

    private String userEmail;

    private String imageUrl1;

    private String imageUrl2;

    private String imageUrl3;

    private Integer price;

    private String title;

    private LocalDateTime updatedAt;

    private Long categoryId; //categoryID도 받아야 되네용
    @Override
    public String getStatus() {
        return super.getStatus();
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return super.getCreatedAt();
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return super.getUpdatedAt();
    }

    @Override
    public void setStatus(String status) {
        super.setStatus(status);
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        super.setCreatedAt(createdAt);
    }

    @Override
    public void setUpdatedAt(LocalDateTime updatedAt) {
        super.setUpdatedAt(updatedAt);
    }
}
