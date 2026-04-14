package com.proyecto.postremaniacapi.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter implements Filter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        String header = req.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {

            String token = header.substring(7);

            try {
                if (jwtUtil.validateToken(token)) {

                    String userId = jwtUtil.extractUserId(token);
                    String username = jwtUtil.extractUsername(token);
                    String rol = jwtUtil.extractRol(token);
                    String email = jwtUtil.extractEmail(token);

                    // 🔐 Autoridades
                    List<SimpleGrantedAuthority> authorities =
                            List.of(new SimpleGrantedAuthority(rol));

                    // 🔐 Autenticación
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    username,
                                    null,
                                    authorities
                            );

                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    // 🔥 ATRIBUTOS DISPONIBLES EN CONTROLADORES
                    req.setAttribute("userId", userId);
                    req.setAttribute("username", username);
                    req.setAttribute("email", email);

                }

            } catch (Exception e) {
                System.out.println("Error validando token: " + e.getMessage());
            }
        }

        chain.doFilter(request, response);
    }
}