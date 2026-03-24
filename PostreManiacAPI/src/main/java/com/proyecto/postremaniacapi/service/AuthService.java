package com.proyecto.postremaniacapi.service;

import com.proyecto.postremaniacapi.dto.*;
import com.proyecto.postremaniacapi.model.Usuario;
import com.proyecto.postremaniacapi.repository.UsuarioRepository;
import com.proyecto.postremaniacapi.config.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;

    public AuthService(UsuarioRepository usuarioRepository, JwtUtil jwtUtil){
        this.usuarioRepository = usuarioRepository;
        this.jwtUtil = jwtUtil;
    }

    // REGISTRO USUARIO NORMAL
    public String register(RegisterRequest request) throws Exception {

        validarPassword(request.getPassword(), request.getConfirmPassword());

        if(usuarioRepository.buscarPorUsername(request.getUsername()) != null){
            throw new RuntimeException("El usuario ya existe");
        }

        Usuario user = new Usuario();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        user.setRol("USER");

        usuarioRepository.guardarUsuario(user);

        return "Usuario registrado correctamente";
    }

    // REGISTRO ADMIN
    public String registerAdmin(RegisterAdminRequest request) throws Exception {

        validarPassword(request.getPassword(), request.getConfirmPassword());

        if(usuarioRepository.buscarPorUsername(request.getUsername()) != null){
            throw new RuntimeException("El usuario ya existe");
        }

        Usuario user = new Usuario();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        user.setRol(request.getRol());

        usuarioRepository.guardarUsuario(user);

        return "Administrador registrado correctamente";
    }

    // LOGIN
    public String login(LoginRequest request) throws Exception {

        Usuario user = usuarioRepository.buscarPorUsername(request.getUsername());

        if(user == null){
            throw new RuntimeException("Usuario no encontrado");
        }

        if(!user.getPassword().equals(request.getPassword())){
            throw new RuntimeException("Contraseña incorrecta");
        }

        System.out.println("Usuario login correctamente");

        return jwtUtil.generateToken(user.getUsername(), user.getRol());
    }

    // VALIDACION CONTRASEÑA
    private void validarPassword(String password, String confirmPassword){

        if(!password.equals(confirmPassword)){
            throw new RuntimeException("Las contraseñas no coinciden");
        }

        if(password.length() < 8){
            throw new RuntimeException("La contraseña debe tener mínimo 8 caracteres");
        }

        if(!password.matches(".*[A-Za-z].*")){
            throw new RuntimeException("Debe contener al menos una letra");
        }
    }
}