package com.bicycle.project.oauthlogin.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER")
public class User extends BaseEntity implements UserDetails {

    private static final long serialVersionUID = 6014984039564979072L;


    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long userIdx; //id 가 아닌 user 식별자.

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

    @Column(name = "USER_ID", length = 64, unique = true)
    @NotNull
    @Size(max = 64)
    private String userEmail;

    @Column(name = "USERNAME", length = 100)
    @NotNull
    @Size(max = 100)
    private String username;

//    @JsonIgnore
    @Column(name = "PASSWORD", length = 128)
    @NotNull
    @Size(max = 128)
    private String password;

//    @Column(name = "EMAIL", length = 512, unique = true)
//    @NotNull
//    @Size(max = 512)
//    private String email;

    @Column(name = "EMAIL_VERIFIED_YN", length = 1)
    //@NotNull
    @Size( max = 1)
    private String emailVerifiedYn;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Column(length=100)
    private String provider;

    @Column(name = "PROFILE_IMAGE_URL", length = 512)
    @Size(max = 512)
    private String profileImageUrl;

//    @Column(name = "CREATED_AT")
//    @NotNull
//    private LocalDateTime createdAt;
//
//    @Column(name = "MODIFIED_AT")
//    @NotNull
//    private LocalDateTime modifiedAt;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

//    public User(Long userIdx, String userEmail, String username, String password, String emailVerifiedYn,
//                List<String> roles, String profileImageUrl/*, LocalDateTime createdAt, LocalDateTime modifiedAt*/) {
//        this.userIdx = userIdx;
//        this.userEmail = userEmail;
//        this.username = username;
//        this.password = password;
////        this.email = email;
//        this.emailVerifiedYn = emailVerifiedYn;
//        this.roles = roles;
//        this.profileImageUrl = profileImageUrl != null ? profileImageUrl : " ";
////        this.createdAt = createdAt;
////        this.modifiedAt = modifiedAt;
//    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String getUserEmail(){
        return this.userEmail;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getPassword(){
        return this.password;
    }


}
