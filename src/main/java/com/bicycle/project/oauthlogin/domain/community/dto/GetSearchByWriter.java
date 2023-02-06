package com.bicycle.project.oauthlogin.domain.community.dto;

import com.bicycle.project.oauthlogin.domain.BasicDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Lob;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetSearchByWriter extends BasicDto {

    private Long communityId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Lob
    private String content;

    private String title;

    private Long categoryId;

    private String userEmail;

    private Long commentCount;

    private Long likeCount;

    @Override
    public String getStatus() {
        return super.getStatus();
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return super.getCreatedAt();
    }

    @Override
    public void setStatus(String status) {
        super.setStatus(status);
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        super.setCreatedAt(createdAt);
    }
}
