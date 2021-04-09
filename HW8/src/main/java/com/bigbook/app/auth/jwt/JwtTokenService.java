package com.bigbook.app.auth.jwt;

import com.bigbook.app.auth.exceptions.JwtAuthException;
import com.bigbook.app.auth.security.SecurityUser;
import com.bigbook.app.user.UserEntity;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtTokenService {

    private final UserDetailsService userDetailsService;

    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.validity}")
    private long validityInMilliseconds;
    @Value("${jwt.cookie.name}")
    private String tokenCookieName;

    public static String EMPTY_TOKEN = "";

    @PostConstruct
    public void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(UserEntity userEntity) {
        Claims claims = Jwts.claims();
        claims.put("id", userEntity.getId());
        claims.put("username", userEntity.getUsername());

        Date now = new Date();
        Date expiration = new Date(now.getTime() + validityInMilliseconds);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthException();
        }
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public static UserEntity getContextUser() {
        SecurityUser securityUser =
                (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return securityUser.getUserEntity();
    }

    public static Integer getCurrentUserId() {
        return getContextUser().getId();
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("username", String.class);
    }

    public String resolveToken(HttpServletRequest request) {
        Cookie tokenCookie = getTokenCookie(request);
        if (tokenCookie == null) return null;
        return tokenCookie.getValue();
    }

    public Cookie getTokenCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;
        Optional<Cookie> cookieOptional = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(tokenCookieName)).findFirst();
        if (cookieOptional.isEmpty()) return null;
        return cookieOptional.get();
    }

    public Cookie generateTokenCookie(String token) {
        Cookie tokenCookie = new Cookie(tokenCookieName, token);
        setupCookie(tokenCookie);
        return tokenCookie;
    }

    public void setupCookie(Cookie cookie) {
        cookie.setHttpOnly(true);
        cookie.setPath("/");
    }
}
