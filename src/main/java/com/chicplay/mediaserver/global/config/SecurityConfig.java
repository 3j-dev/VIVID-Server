package com.chicplay.mediaserver.global.config;

import com.chicplay.mediaserver.domain.user.domain.Role;
import com.chicplay.mediaserver.global.auth.JwtAuthExceptionFilter;
import com.chicplay.mediaserver.global.auth.JwtAuthFilter;
import com.chicplay.mediaserver.global.auth.JwtProviderService;
import com.chicplay.mediaserver.domain.user.application.OAuthSuccessHandler;
import com.chicplay.mediaserver.global.auth.RestAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpSession;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2UserService auth2UserService;

    private final OAuthSuccessHandler oAuthSuccessHandler;

    private final JwtProviderService jwtProviderService;


    @Bean
    public WebSecurityCustomizer configure() {

        // filter 안타게끔
        return (web) -> web.ignoring().mvcMatchers(
                "/auth/token/**", "/swagger-ui/**","/api/test/**", "/v3/api-docs/**","/login/oauth2/code"
        );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .csrf().disable()
                .formLogin().disable() // 로그인 폼 미사용
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .headers().frameOptions().disable()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new RestAuthenticationEntryPoint()) // 인증,인가가 되지 않은 요청 시 발생시
                .and()
                .authorizeRequests()
                .antMatchers("/"
                        , "/css/**"
                        , "/images/**"
                        , "/js/**"
                        , "/h2-console/**"
                ).permitAll()
                .antMatchers("/login/**", "/auth/**").permitAll() // Security 허용 url
                .antMatchers("/api/**").hasRole(Role.USER.name())   // 모든 api 요청에 대해 user 권한
                .anyRequest().authenticated()   // 나머지 요청에 대해서 권한이 있어야함
                .and()
                .oauth2Login()
                .successHandler(oAuthSuccessHandler)
                .userInfoEndpoint().userService(auth2UserService)
                .and()
                .authorizationEndpoint().baseUri("/login"); // 소셜 로그인 url



        http
                .addFilterBefore(new JwtAuthFilter(jwtProviderService), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthExceptionFilter(), JwtAuthFilter.class);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
