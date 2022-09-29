package com.chicplay.mediaserver.global.auth;

import com.chicplay.mediaserver.domain.user.application.OAuthUserService;
import com.chicplay.mediaserver.domain.user.domain.Role;
import com.chicplay.mediaserver.domain.user.domain.UserAuthToken;
import com.chicplay.mediaserver.domain.user.dto.UserNewTokenReqeust;
import com.chicplay.mediaserver.domain.user.exception.HeaderAccessTokenInvalidException;
import com.chicplay.mediaserver.domain.user.exception.RefreshTokenExpiredException;
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
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtProviderService {

    private final HttpSession httpSession;

    @Value("${jwt.password}")
    private String secretKey;

    @Value("${spring.redis-user.session.timeout}")
    private int sessionTime;

    private Key key;

    private final long tokenPeriod = 1000L * 60L * 10L;     // 10분

    private final long refreshPeriod = 1000L * 60L * 60L * 24L * 10L;      // 10일

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

    public UserAuthToken getTokenFromRedisSession() {

        // get refreshToken from Redis Session
        String refreshToken = (String)httpSession.getAttribute("refreshToken");

        // redis에 session data가 없음.
        if (!StringUtils.hasText(refreshToken)){

            // not found exception
            throw new RefreshTokenNotFoundException();
        }

        // 유효한 토큰 일 경우 re-issue access token return
        if (validateToken(refreshToken)) {

            String email = getEmail(refreshToken);
            UserAuthToken userAuthToken = generateToken(email, Role.USER.name());

            return userAuthToken;
        }

        throw new RefreshTokenNotFoundException();
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

    public String parseBearerToken(HttpServletRequest request) {

        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return "";
    }

    public String getEmail(String token) {

        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token).getBody().getSubject();
        } catch (ExpiredJwtException expiredJwtException) {

            return expiredJwtException.getClaims().getSubject();
        }
    }
}
