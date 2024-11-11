package com.thocodeonline.sheepshop.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Tắt CSRF protection vì đây là API REST
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/public/**").permitAll() // Bỏ qua bảo mật cho các endpoint công khai
                        .anyRequest().authenticated() // Yêu cầu xác thực cho các yêu cầu khác
                )
                .httpBasic(); // Sử dụng xác thực cơ bản

        return http.build();
    }
}
