//package com.bicycle.project.oauthlogin.api.controller.auth.dto;
//
//import com.bicycle.project.oauthlogin.api.entity.user.User;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import lombok.*;
//
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Getter
//@Setter
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//public class UserDto {
//
//    @NotNull
//    @Size(min=50, max=100)
//    private String userEmail;
//
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    @NotNull
//    @Size(min=50, max=100)
//    private String password;
//
//    private Set<AuthorityDto> authorityDtoSet;
//
//    public static UserDto from(User user){
//        if(user ==null) return null;
//
//        return UserDto.builder()
//                .userEmail(user.getEmail())
//                .authorityDtoSet(user.getRoleType().stream()
//                        .map(authority-> AuthorityDto.builder().authorityName(authority.getAuthorityName()).build()
//                                .collect(Collectors.toSet())))
//                .build();
//    }
//}
