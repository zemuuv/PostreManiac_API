package com.proyecto.postremaniacapi.controller;

import com.proyecto.postremaniacapi.dto.*;
import com.proyecto.postremaniacapi.service.AuthService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    // Usuario normal
    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) throws Exception {
        return authService.register(request);
    }

    // 🔥 Admin (solo Postman o uso interno)
    @PostMapping("/register-admin")
    public String registerAdmin(@RequestBody RegisterAdminRequest request) throws Exception {
        return authService.registerAdmin(request);
    }

    // 🔐 Login
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginRequest request) throws Exception {
        return authService.login(request);
    }
}