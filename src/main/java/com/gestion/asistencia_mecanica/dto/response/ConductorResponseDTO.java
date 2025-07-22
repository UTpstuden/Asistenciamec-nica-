package com.gestion.asistencia_mecanica.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ConductorResponseDTO extends UserResponseDTO {
    private String nombres;
    private String apellidos;
    private String dni;
    private VehiculoResponseDTO vehiculo;

    private int serviciosSolicitados;
    private int serviciosCompletados;
    private double calificacionPromedio;
}
