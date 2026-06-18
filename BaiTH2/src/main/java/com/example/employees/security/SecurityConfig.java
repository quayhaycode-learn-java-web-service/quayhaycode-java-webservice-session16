package com.example.employees.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Bean mã hóa mật khẩu
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    // Bước 1: Cấu hình danh sách người dùng tĩnh (In-Memory)
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails alice = User.builder()
                .username("alice")
                .password(passwordEncoder.encode("password123"))
                .roles("USER")
                .build();
        UserDetails bob = User.builder()
                .username("bob")
                .password(passwordEncoder.encode("password456"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(alice, bob);
    }
    // Bước 2: Cấu hình SecurityFilterChain (Phân quyền dùng Lambda DSL)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Tắt CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**").permitAll() // Cho phép truy cập tự do vào auth
                        .anyRequest().authenticated()                  // Khóa tất cả các API còn lại
                )
                .httpBasic(Customizer.withDefaults()); // Bật HTTP Basic Auth để test Postman
        return http.build();
    }
}