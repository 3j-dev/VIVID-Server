package com.chicplay.mediaserver.global.auth;

import com.chicplay.mediaserver.global.error.ErrorResponse;
import com.chicplay.mediaserver.global.error.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthExceptionFilter extends OncePerRequestFilter {




    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // move to next filter
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException expiredJwtException) {

            // access token 만료 throw
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, ErrorCode.ACCESS_TOKEN_EXPIRED);
        } catch (Exception exception) {

            // 이외의 token 오류 경우 throw
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, ErrorCode.ACCESS_TOKEN_ILLEGAL_STATE);
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, ErrorCode errorCode) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(
               ErrorResponse.from(errorCode)
        ));
    }
}
