package com.bigbook.app.auth;

import com.bigbook.app.auth.dto.AuthRequestDto;
import com.bigbook.app.auth.dto.RedirectResponseDto;
import com.bigbook.app.auth.dto.RegisterRequestDto;
import com.bigbook.app.auth.exceptions.InvalidUsernameOrPasswordException;
import com.bigbook.app.auth.exceptions.UsernameExistsException;
import com.bigbook.app.auth.jwt.JwtTokenService;
import com.bigbook.app.auth.permissions.Permission;
import com.bigbook.app.auth.permissions.PermissionEntity;
import com.bigbook.app.auth.security.SecurityUser;
import com.bigbook.app.user.UserEntity;
import com.bigbook.app.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final String ROOT_URL = "/";
    private final String LOGIN_URL = "/auth/login";

    public RedirectResponseDto login(AuthRequestDto authRequestDto, HttpServletResponse servletResponse) {
        try {
            String username = authRequestDto.getUsername();
            String password = authRequestDto.getPassword();
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));

            UserEntity userEntity = ((SecurityUser) authentication.getPrincipal()).getUserEntity();
            String accessToken = jwtTokenService.createToken(userEntity);
            Cookie tokenCookie = jwtTokenService.generateTokenCookie(accessToken);
            servletResponse.addCookie(tokenCookie);
            return RedirectResponseDto.of(ROOT_URL);
        } catch (BadCredentialsException e) {
            throw new InvalidUsernameOrPasswordException();
        }
    }

    public RedirectResponseDto register(RegisterRequestDto registerRequestDto) {
        if (userRepository.findByUsername(registerRequestDto.getUsername()).isPresent()) {
            throw new UsernameExistsException();
        }

        UserEntity userEntity = UserEntity.builder()
                .username(registerRequestDto.getUsername())
                .permissions(List.of(PermissionEntity.fromId(Permission.ADD_BOOK_TO_FAVORITE.ordinal())))
                .password(passwordEncoder.encode(registerRequestDto.getPassword()))
                .build();
        userRepository.saveAndFlush(userEntity);
        return RedirectResponseDto.of(LOGIN_URL);
    }

    public void logout(HttpServletResponse servletResponse) {
        Cookie tokenCookie = jwtTokenService.generateTokenCookie("");
        tokenCookie.setMaxAge(0);
        servletResponse.addCookie(tokenCookie);
    }

}
