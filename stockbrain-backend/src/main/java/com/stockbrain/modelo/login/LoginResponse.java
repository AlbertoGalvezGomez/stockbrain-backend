package com.stockbrain.modelo.login;

public class LoginResponse {
    private Long id;
    private String email;
    private String rol;
    private String message;

    public LoginResponse() {}

    public LoginResponse(Long id, String email, String rol, String message) {
        this.id = id;
        this.email = email;
        this.rol = rol;
        this.message = message;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}