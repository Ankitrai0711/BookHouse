package com.example.bookstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.bookstore.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // ✅ new session config
            .authorizeHttpRequests(auth -> auth
                // Public endpoints
                .antMatchers("/users/login", "/users/register", "/loginRegister.html", 
                                 "/buyerui.html", "/bookui.html", "/orderview.html").permitAll()

                // ADMIN only
                .antMatchers(HttpMethod.POST, "/book").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/book/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/book/delete/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/orders").hasRole("ADMIN")

                // USER only
                .antMatchers(HttpMethod.POST, "/orders/place").hasRole("USER")

                // Both USER & ADMIN
                .antMatchers(HttpMethod.GET, "/book", "/book/getById/{id}")
                    .hasAnyRole("USER", "ADMIN")

                // Any other must be authenticated
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }
}
