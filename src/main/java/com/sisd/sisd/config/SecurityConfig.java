package com.sisd.sisd.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sisd.sisd.exception.CustomAccessDeniedException;
import com.sisd.sisd.exception.CustomUnAuthorizeException;
import com.sisd.sisd.security.JwtFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    JwtFilter filter;
    @Bean
    PasswordEncoder getPasswordEncoder() {return new BCryptPasswordEncoder();}

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .exceptionHandling(ex -> ex
                .authenticationEntryPoint(new CustomUnAuthorizeException())
                .accessDeniedHandler(new CustomAccessDeniedException()))
        .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**",
                        "/student/register",
                        "/student/upload-student-photo",
                        "/student/upload-parent-photo",
                        "/v3/api-docs/**",
                        "/swagger-ui/**")
                .permitAll()

                        .anyRequest().authenticated())
                .addFilterAfter(filter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
