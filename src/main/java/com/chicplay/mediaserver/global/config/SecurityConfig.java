package com.chicplay.mediaserver.global.config;

import com.chicplay.mediaserver.domain.user.domain.Role;
import com.chicplay.mediaserver.global.auth.JwtAuthFilter;
import com.chicplay.mediaserver.global.auth.JwtProvider;
import com.chicplay.mediaserver.domain.user.application.OAuthSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2UserService auth2UserService;

    private final OAuthSuccessHandler oAuthSuccessHandler;

    private final JwtProvider jwtProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/"
                        , "/css/**"
                        , "/images/**"
                        , "/js/**"
                        , "/h2-console/**"
                ).permitAll()
                .antMatchers("/auth/**", "/oauth2/**").permitAll() // Security 허용 url
                .antMatchers("/api/**").hasRole(Role.USER.name())   // 모든 api 요청에 대해 user 권한
                .anyRequest().authenticated()   // 나머지 요청에 대해서 권한이 있어야함
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .oauth2Login()
                .successHandler(oAuthSuccessHandler)
                .userInfoEndpoint().userService(auth2UserService);

        http.addFilterBefore(new JwtAuthFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
