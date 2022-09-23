package com.chicplay.mediaserver.global.auth;

import com.chicplay.mediaserver.domain.user.dto.UserLoginRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = parseBearerToken(request);

        // Validation Access Token

        if (StringUtils.hasText(token) && jwtProvider.validateToken(token)) {

            String email = jwtProvider.getEmail(token);

            String accessToken = jwtProvider.getAccessToken(token);

            UserLoginRequest user = UserLoginRequest.builder().email(email).build();

            Authentication authentication = getAuthentication(user, request);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }


        // 다음 필터 실행
        filterChain.doFilter(request, response);
    }

    public Authentication getAuthentication(UserLoginRequest user, HttpServletRequest request) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null,
                Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));


        return authenticationToken;

    }

    private String parseBearerToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
