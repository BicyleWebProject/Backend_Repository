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
public class GetUserRecentList extends BasicDto {

    /*
    제목, 하트수, 댓글수 가져오기
     */
    private String userEmail;

    private Long communityId;

    private LocalDateTime updatedAt;

    private Long countLike; //좋아요 숫자

    private Long commentLike; //댓글 숫자

    @Lob
    private String content;

    private String title;

    private String communityImageUrl;

    private String categoryId;

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
