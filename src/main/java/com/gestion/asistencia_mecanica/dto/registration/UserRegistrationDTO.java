package com.gestion.asistencia_mecanica.dto.registration;

import com.gestion.asistencia_mecanica.validation.Password;
import com.gestion.asistencia_mecanica.validation.SoloLetras;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserRegistrationDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @SoloLetras(message = "El nombre solo debe contener letras, espacios y/o guiones")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombres;

    @NotBlank(message = "El apellido es obligatorio")
    @SoloLetras(message = "El apellido solo debe contener letras, espacios y/o guiones")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    private String apellidos;

    @NotBlank(message = "El DNI es obligatorio")
    @Size(min = 8, max = 8, message = "El DNI debe tener 8 dígitos")
    private String dni;

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(min = 9, max = 9, message = "El teléfono debe tener 9 dígitos")
    private String telefono;

    @Email(message = "El email no es válido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Password
    private String password;

}
