package com.bigbook.app.auth.permission;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Permission {
    ADD_NEW_BOOK, ADD_BOOK_TO_FAVORITE;

    public SimpleGrantedAuthority getAuthority() {
        return new SimpleGrantedAuthority(this.name());
    }
}

