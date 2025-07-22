package com.gestion.asistencia_mecanica.dto.login;

import com.gestion.asistencia_mecanica.validation.Password;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class LoginRequest {
    @Email
    private String email;
    @Password
    private String password;
}