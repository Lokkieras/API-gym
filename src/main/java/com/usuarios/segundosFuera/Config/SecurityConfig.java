package com.usuarios.segundosFuera.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/gym/public/**").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(basic -> {})
                .formLogin(form -> form.disable());

        return http.build();
    }
}

