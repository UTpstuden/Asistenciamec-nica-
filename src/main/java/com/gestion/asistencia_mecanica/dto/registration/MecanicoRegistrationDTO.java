package com.gestion.asistencia_mecanica.dto.registration;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class MecanicoRegistrationDTO extends UserRegistrationDTO {
    @NotBlank(message = "La experiencia es obligatoria")
    private String experiencia;
    @NotBlank(message = "La especialidad es obligatoria")
    private String especialidad;
    @NotBlank(message = "El certificado es obligatorio")
    private String certificaciones;
    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;
    private BigDecimal calificacionPromedio;
    private TallerRegistrationDTO taller;
}
