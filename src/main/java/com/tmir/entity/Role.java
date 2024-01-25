package com.tmir.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    ROLE_SUPER,
    ROLE_CAFE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
