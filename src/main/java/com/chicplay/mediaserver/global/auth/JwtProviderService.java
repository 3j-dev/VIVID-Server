package com.chicplay.mediaserver.global.auth;

import com.chicplay.mediaserver.domain.user.application.OAuthUserService;
import com.chicplay.mediaserver.domain.user.domain.Role;
import com.chicplay.mediaserver.domain.user.domain.UserAuthToken;
import com.chicplay.mediaserver.domain.user.exception.RefreshTokenNotFoundException;
import com.chicplay.mediaserver.global.error.exception.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtProviderService {

    @Value("${jwt.password}")
    private String secretKey;
    private Key key;

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
        } catch (ExpiredJwtException expiredJwtException) {
            throw expiredJwtException;
        } catch (JwtException jwtException) {
            throw jwtException;
        }
    }

    public String getEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token).getBody().getSubject();
    }
}
