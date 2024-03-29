package com.bigbook.app.auth.security;

import com.bigbook.app.user.UserEntity;
import lombok.Getter;
import org.springframework.security.core.userdetails.User;

import java.util.stream.Collectors;


@Getter
public class SecurityUser extends User {

    private final UserEntity userEntity;

    public SecurityUser(UserEntity userEntity) {
        super(userEntity.getUsername(), userEntity.getPassword(),
                userEntity.getPermissions().stream()
                        .map(p -> p.getPermission().getAuthority())
                        .collect(Collectors.toList()));
        this.userEntity = userEntity;
    }
}
