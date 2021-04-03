package com.bigbook.app.auth.security;

import com.bigbook.app.user.UserEntity;
import com.bigbook.app.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor

@Service
public class SecurityUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.getByUsername(username)
                .orElseThrow(() -> {
                    return new UsernameNotFoundException("Invalid username");
                });
        return SecurityUser.from(userEntity);
    }
}
