package com.proyecto.postremaniacapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> {})
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // 🔓 PUBLICO
                        .requestMatchers("/auth/**").permitAll()

                        // 📦 PRODUCTOS
                        .requestMatchers(HttpMethod.GET, "/productos/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/productos/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/productos/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/productos/**").hasAuthority("ADMIN")

                        // 🛒 PEDIDOS
                        .requestMatchers(HttpMethod.POST, "/api/pedidos").authenticated() // crear pedido
                        .requestMatchers(HttpMethod.GET, "/api/pedidos/mis-pedidos").authenticated() // cliente

                        // 👑 ADMIN PEDIDOS
                        .requestMatchers("/api/pedidos/**").hasAuthority("ADMIN")

                        // 🔒 TODO LO DEMÁS
                        .anyRequest().authenticated()
                )

                // 🔥 JWT FILTER
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOriginPatterns(List.of("*"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}