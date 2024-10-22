package com.example.backend.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration // @Service more descriptive to what this bean does
@EnableWebSecurity //  Enables Spring Security
public class SecurityConfig {

    @Value("${app.url}")
    private String appUrl; // We redirect to this URL on successful login

    @Bean
    // This bean is what configures all aspects of Spring Security
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(a -> a
                .requestMatchers("/api/**").authenticated() // This covers /api/hello, /api/hello/1, everything with a /api prefix
                .anyRequest().permitAll()
            )
            .csrf(AbstractHttpConfigurer::disable) // This protection is only applicable for server side rendered apps
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)) // Store logged in user in HTTP session
            .exceptionHandling(e -> e
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))) // If user fails to login return 401 unauthorized status
            .oauth2Login(o -> o.defaultSuccessUrl(appUrl)) // Redirect to appUrl if login is successful
            .logout(l -> l.logoutUrl("/api/auth/logout")
                    .logoutSuccessHandler((request, response, authentication) -> response.setStatus(HttpStatus.OK.value())));
        return http.build();
    }

}