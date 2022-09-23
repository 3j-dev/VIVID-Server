package com.chicplay.mediaserver.global.auth;

import com.chicplay.mediaserver.domain.user.application.OAuthUserService;
import com.chicplay.mediaserver.domain.user.domain.Role;
import com.chicplay.mediaserver.domain.user.domain.UserAuthToken;
import com.chicplay.mediaserver.domain.user.exception.RefreshTokenNotFoundException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.password}")
    private String secretKey;
    private Key key;

    private final OAuthUserService oAuthUserService;

    private final long tokenPeriod = 1000L * 60L * 10L;     // 10분
    //private final long tokenPeriod = 1000L;
    private final long refreshPeriod = 1000L * 60L * 60L * 24L * 30L * 3L;      // 3주

    @PostConstruct
    protected void init() {
        this.key = Keys.hmacShaKeyFor(Base64.getEncoder().encode(secretKey.getBytes()));
    }

    public UserAuthToken generateToken(String email, String role) {

        Claims claims = Jwts.claims().setSubject(email);
        claims.put("role", role);

        Date now = new Date();

        return new UserAuthToken(
                email,
                Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + tokenPeriod))
                        .signWith(key, SignatureAlgorithm.HS256)
                        .compact(),
                Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + refreshPeriod))
                        .signWith(key, SignatureAlgorithm.HS256)
                        .compact());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception exception) {
            throw exception;
        }
    }


    // 만료된 토큰인 경우, refresh token을 확인한 후 재발급, 아니면 그대로.
    public String getAccessToken(String token) {

        try {
            // 만료되지 않았을 경우, 그대로 access token issue.
            if (validateToken(token)) {
                return token;
            }
        } catch (ExpiredJwtException expiredJwtException) {

            // 만료된 경우, access token re-issue
            // 고치기.... expired return 하면 프론트에서 다시 api 다시 쏘게씁
            String email = getEmail(token);
            String refreshToken = getRefreshToken(email);

            // 유효한 토큰 일 경우 re-issue access token return
            if (validateToken(refreshToken)) {
                UserAuthToken userAuthToken = generateToken(email, Role.USER.name());
                return userAuthToken.getToken();
            }

        } catch (SignatureException signatureException) {
            throw signatureException;
        } catch (UnsupportedJwtException unsupportedJwtException) {
            throw unsupportedJwtException;
        } catch (IllegalStateException illegalStateException) {
            throw illegalStateException;
        }

        return "";
    }

    @Transactional
    public String getRefreshToken(String email) {
        try {

            String refreshTokenFromEmail = oAuthUserService.getRefreshTokenFromEmail(email);
            return refreshTokenFromEmail;

        } catch (RefreshTokenNotFoundException refreshTokenNotFoundException) {
            throw refreshTokenNotFoundException;
        }


    }

    public String getEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token).getBody().getSubject();
    }
}
