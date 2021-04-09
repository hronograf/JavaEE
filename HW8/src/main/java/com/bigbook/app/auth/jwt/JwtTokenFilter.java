package com.bigbook.app.auth.jwt;

import com.bigbook.app.auth.exceptions.JwtAuthException;
import com.bigbook.app.auth.security.SecurityConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor

@Component
public class JwtTokenFilter extends GenericFilterBean {

    private final JwtTokenService jwtTokenService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        boolean shouldBeIgnored = Arrays.stream(SecurityConfig.NOT_SECURED_URLS)
                .anyMatch(ignored -> httpServletRequest.getRequestURI().equals(ignored));
        log.debug("Filtering request to " + httpServletRequest.getRequestURI());

        try {
            Cookie tokenCookie = jwtTokenService.getTokenCookie(httpServletRequest);

            if (tokenCookie == null && shouldBeIgnored) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }

            if (tokenCookie == null) {
                deleteTokenAndRedirectToLogin(httpServletResponse);
                return;
            }
            String token = tokenCookie.getValue();
            if (jwtTokenService.validateToken(token)) {
                    Authentication authentication = jwtTokenService.getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                deleteTokenAndRedirectToLogin(httpServletResponse);
                return;
            }
        } catch (JwtAuthException | UsernameNotFoundException e) {
            deleteTokenAndRedirectToLogin(httpServletResponse);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void deleteTokenAndRedirectToLogin(HttpServletResponse httpServletResponse) throws IOException {
        SecurityContextHolder.clearContext();
        log.debug("Redirect to login page");
        Cookie emptyToken = jwtTokenService.generateTokenCookie(JwtTokenService.EMPTY_TOKEN);
        emptyToken.setMaxAge(0); // delete token
        httpServletResponse.addCookie(emptyToken);
        httpServletResponse.sendRedirect("/auth/login");
    }
}
