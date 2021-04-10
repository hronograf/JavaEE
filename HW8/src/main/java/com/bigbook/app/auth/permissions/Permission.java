package com.bigbook.app.auth.permissions;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Permission {
    ADD_NEW_BOOK;

    public SimpleGrantedAuthority getAuthority() {
        return new SimpleGrantedAuthority(this.name());
    }
}

