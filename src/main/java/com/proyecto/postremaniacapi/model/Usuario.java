package com.proyecto.postremaniacapi.model;

public class Usuario {

    private String id;
    private String username;
    private String email;
    private String password;
    private String rol;

    public Usuario(String rol, String id, String username, String email, String password) {
        this.rol = rol;
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Usuario() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
