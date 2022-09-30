package com.chicplay.mediaserver.domain.user.domain;

import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

public class UserSessionDetails implements Serializable {

    private String email;

    @Builder
    public UserSessionDetails(String email, String name,String picture) {
        this.email = email;
    }


}
