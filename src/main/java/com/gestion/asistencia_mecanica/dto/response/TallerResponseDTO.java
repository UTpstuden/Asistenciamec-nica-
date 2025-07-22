package com.gestion.asistencia_mecanica.dto.response;

import lombok.Data;

@Data
public class TallerResponseDTO {
    private String nombre;
    private String direccion;
    private String distrito;
    private String radioCobertura;
    private String tipoServicio;
}