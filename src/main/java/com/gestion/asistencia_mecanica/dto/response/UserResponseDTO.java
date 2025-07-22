package com.gestion.asistencia_mecanica.dto.response;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class UserResponseDTO {
    private String nombres;
    private String apellidos;
    private String telefono;

}
